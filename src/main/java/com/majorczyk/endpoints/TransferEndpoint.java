package com.majorczyk.endpoints;

import com.majorczyk.database.AccountRepository;
import com.majorczyk.database.UserRepository;
import com.majorczyk.model.Account;
import com.majorczyk.model.User;
import com.majorczyk.services.intefraces.AccountService;
import com.majorczyk.services.intefraces.TransferService;
import com.majorczyk.services.intefraces.UserService;
import com.majorczyk.soap.generated.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Created by Piotr on 2018-01-19.
 */
@Endpoint
public class TransferEndpoint {

    private final String NAMESPACE = "com/majorczyk/soap/account";

    @Autowired
    UserService userService;

    @Autowired
    TransferService transferService;

    @Autowired
    AccountService accountService;

    @PayloadRoot(namespace = NAMESPACE,
            localPart = "ExternalTransfer")
    @ResponsePayload
    public TransferResponse externalTransfer(@RequestPayload ExternalTransfer request) {
        Account destinationAccount = accountService.findByAccountNumber(request.getAccountTo());
        Account sourceAccount = accountService.findByAccountNumber(request.getAccountFrom());
        transferService.saveExternalTransfer(sourceAccount.getAccountNumber(), destinationAccount.getAccountNumber(), request.getAmount(), request.getName(), request.getTitle());
        TransferResponse response = new TransferResponse();
        response.setStatus(TransferStatus.OK);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE,
            localPart = "InternalTransfer")
    @ResponsePayload
    public TransferResponse internalTransfer(@RequestPayload InternalTransfer request) {
        User user = userService.findByLogin(request.getToken());
//        Bank.checkControlSum(transfer.getAccountFrom());
//        Bank.checkControlSum(transfer.getAccountTo());
        transferService.saveInternalTransfer(request.getAccountFrom(), request.getAccountTo(), request.getAmount());
        TransferResponse response = new TransferResponse();
        response.setStatus(TransferStatus.OK);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE,
            localPart = "Payment")
    @ResponsePayload
    public TransferResponse payment(@RequestPayload Payment request) {
//        Bank.checkControlSum(transfer.getAccountFrom());
        transferService.savePayment(request.getAccountTo(), request.getAmount());
        TransferResponse response = new TransferResponse();
        response.setStatus(TransferStatus.OK);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE,
            localPart = "Withdrawal")
    @ResponsePayload
    public TransferResponse withdrawal(@RequestPayload Withdrawal request) {
//        Bank.checkControlSum(transfer.getAccountFrom());
        transferService.saveWithdrawal(request.getAccountFrom(), request.getAmount());
        TransferResponse response = new TransferResponse();
        response.setStatus(TransferStatus.OK);
        return response;
    }
}