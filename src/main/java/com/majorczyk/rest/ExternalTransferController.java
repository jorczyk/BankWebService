package com.majorczyk.rest;

        import com.majorczyk.database.AccountRepository;
        import com.majorczyk.database.OperationRepository;
        import com.majorczyk.model.Account;
        import com.majorczyk.model.Operation;
        import com.majorczyk.model.Transfer;
        import com.majorczyk.security.ValidationUtils;
        import org.json.JSONObject;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

/**
 * REST inter-bank transfers controller
 */

@RestController
public class ExternalTransferController {

//    @Autowired
//    AccountService accountService;
//
//    @Autowired
//    TransferService transferService;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    OperationRepository operationRepository;

    /**
     * Used for inter-bank transfers
     * @param accountNumber - destination account number
     * @param message - body of request message
     * @return response status and body
     */

    @RequestMapping(method = RequestMethod.POST,value = "/accounts/{accountNumber}/history")
    public ResponseEntity externalTransfer(@PathVariable String accountNumber,@RequestBody Transfer message){
        Account account = accountExists(accountNumber);
        if (account != null) {
            String validationResult = ValidationUtils.validateTransfer(message, accountNumber);
            if (validationResult.length() == 0) {
                account.setBalance(account.getBalance()+message.getAmount());
                Operation operation = new Operation(accountNumber, message.getTitle(), message.getAmount(), message.getSourceAccountNo(), account.getBalance());
                operationRepository.save(operation);
                accountRepository.save(account);
                return ResponseEntity.status(201).build();
            } else {
                JSONObject json = new JSONObject();
                json.put("error_field", validationResult);
                json.put("error", ValidationUtils.getErrorMessage(validationResult));
                return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private Account accountExists(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

}
