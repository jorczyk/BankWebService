package com.majorczyk.rest;

        import com.majorczyk.model.Account;
        import com.majorczyk.model.Transfer;
        import com.majorczyk.model.TransferType;
        import com.majorczyk.services.intefraces.AccountService;
        import com.majorczyk.services.intefraces.TransferService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

/**
 * REST inter-bank transfers controller
 */

@RestController
public class ExternalTransferController {

    @Autowired
    AccountService accountService;

    @Autowired
    TransferService transferService;

    /**
     * Used for inte-bank transfers
     * @param accountNumber - destination account number
     * @param message - body of request message
     * @return response status and body
     */

    @RequestMapping(method = RequestMethod.POST,value = "/accounts/{accountNumber}/history")
    public ResponseEntity externalTransfer(@PathVariable String accountNumber,@RequestBody RequestMessage message){
        //TODO

        Account destinationAccount = accountService.findByAccountNumber(accountNumber);

        if (destinationAccount == null) {
            ResponseMessage response = new ResponseMessage("nr_konta","Account doesn't exist");
            return new ResponseEntity<ResponseMessage>(response, HttpStatus.BAD_REQUEST);
        }

        if (message.getAmount() <= 0) {
            ResponseMessage response = new ResponseMessage("ammount","Amount must be greater than 0");
            return new ResponseEntity<ResponseMessage>(response, HttpStatus.BAD_REQUEST);
        }

        destinationAccount.setBalance(destinationAccount.getBalance() + message.getAmount());
        accountService.save(destinationAccount);
        Transfer transfer = new Transfer();
        transfer.setAmmount(message.getAmount());
        transfer.setDestinationAccountNo(destinationAccount.getAccountNumber());
//        transfer.setName(payload.getName());
        transfer.setSourceAccountNo(message.getSource());
        transfer.setTitle(message.getTitle());
        transfer.setTransferType(TransferType.EXTERNAL_TANSFER);
        transferService.save(transfer);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

}
