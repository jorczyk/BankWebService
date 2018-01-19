package com.majorczyk.endpoints;

import com.majorczyk.database.AccountRepository;
import com.majorczyk.database.UserRepository;
import com.majorczyk.model.Account;
import com.majorczyk.model.Transfer;
import com.majorczyk.model.User;
import com.majorczyk.services.implementations.AccountServiceImpl;
import com.majorczyk.services.intefraces.AccountService;
import com.majorczyk.services.intefraces.TransferService;
import com.majorczyk.services.intefraces.UserService;
import com.majorczyk.soap.generated.*;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr on 2018-01-19.
 */
@Endpoint
@NoArgsConstructor
public class AccountEndpoint {

    private final String NAMESPACE = "com/majorczyk/soap/account";

    @Autowired
    AccountService accountService;

    @Autowired
    TransferService transferService;

    @Autowired
    UserService userService;

    @PayloadRoot(namespace = NAMESPACE,
            localPart = "GetAccountHistoryRequest")
    @ResponsePayload
    public GetAccountHistoryResponse getAccountHistory(@RequestPayload GetAccountHistoryRequest request){
        GetAccountHistoryResponse response = new GetAccountHistoryResponse();
        User user = userService.findByLogin(request.getToken());
        List<Transfer> transfers = accountService.getAccountHistory(request.getAccountId());

//        Bank.checkControlSum(accountHistory.getAccountNumber());
        for(Transfer transfer : transfers){
            response.getAccountHistory().add(transfer.convertToResponse());
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE,
            localPart = "Login")
    @ResponsePayload
    public LoginResponse login(@RequestPayload Login request){
        LoginResponse response = new LoginResponse();
        String userLogin = request.getLogin();
        String userPassword = request.getPassword();
//        response.setUserId(userService.findByLoginAndPassword(userLogin,userPassword).getId());
        return response;
    }

//    @PayloadRoot(namespace = NAMESPACE,
//            localPart = "CreateBankAccount")
//    @ResponsePayload
//    public CreateBankAccountResponse createBankAccount(@RequestPayload CreateBankAccount request){
//        CreateBankAccountResponse response = new CreateBankAccountResponse();
////        Integer userId = request.getUserId();
////        Account account = new Account();
////        account.setBalance(0);
////        account.setAccountNumber("123");
////        response.setAccountId(account.getAccountNumber());
//
//        //dzialania na response
//        return response;
//    }

    //    @PayloadRoot(namespace = NAMESPACE,
//            localPart = "CreateUserAccount")
//    public void createUserAccount(@RequestPayload CreateUserAccount request){
//        String userLogin = request.getLogin();
//        String userPassword = request.getPassword();
//        User user = new User();
//        user.setLogin(userLogin);
//        user.setPassword(userPassword);
//        userRepository.insert(user);
//        //dzialania na response
//
//        //dzialania na response
//    }




}
