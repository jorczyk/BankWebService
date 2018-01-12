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
 * Created by Piotr on 2018-01-11.
 */

@RestController
public class ExternalTransferController {

    @Autowired
    AccountService accountService;

    @Autowired
    TransferService transferService;

    @RequestMapping(method = RequestMethod.POST,value = "/accounts/{accountNumber}/history")
    public ResponseEntity externalTransfer(@PathVariable String accountNumber,@RequestBody RequestMessage payload){
        Account destinationAccount = accountService.getAccountByNumber(accountNumber);

        if (destinationAccount == null) {
            ResponseMessage response = new ResponseMessage("nr_konta","Account doesn't exist");
            return new ResponseEntity<ResponseMessage>(response, HttpStatus.BAD_REQUEST);
        }

        if (payload.getAmount() <= 0) {
            ResponseMessage response = new ResponseMessage("ammount","Amount must be greater than 0");
            return new ResponseEntity<ResponseMessage>(response, HttpStatus.BAD_REQUEST);
        }

        destinationAccount.setBalance(destinationAccount.getBalance() + payload.getAmount());
        accountService.save(destinationAccount);
        Transfer transfer = new Transfer(null,
                TransferType.EXTERNAL_TANSFER,
                payload.getSource(),
                destinationAccount.getAccountNumber(),
                payload.getAmount(),
                payload.getTitle(),
                payload.getName());
        transferService.save(transfer);
        return ResponseEntity.status(HttpStatus.OK).build();

    }
}
