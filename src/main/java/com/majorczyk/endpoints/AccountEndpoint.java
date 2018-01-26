package com.majorczyk.endpoints;

import com.majorczyk.database.AccountRepository;
import com.majorczyk.exceptions.ServiceFault;
import com.majorczyk.exceptions.ServiceFaultException;
import com.majorczyk.model.Account;
import com.majorczyk.security.TokenGenerator;
import com.majorczyk.soap.generated.*;
import com.majorczyk.utils.Wrapper;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

/**
 * API endpoint for bank accounts
 */
@Endpoint
@NoArgsConstructor
public class AccountEndpoint {

    private final String NAMESPACE = "com/majorczyk/soap/account";

    @Autowired
    TokenGenerator tokenGenerator ;
    @Autowired
    AccountRepository accountRepository;

    /**
     * Gets user's accounts
     * @param request - request object containing token
     * @return response with list of user's accounts
     */
    @PayloadRoot(namespace = NAMESPACE,
            localPart = "GetUserAccountsRequest")
    @ResponsePayload
    public GetUserAccountsResponse getUserAccountsRequest(@RequestPayload GetUserAccountsRequest request){
        GetUserAccountsResponse response = new GetUserAccountsResponse();
//        if (!tokenGenerator.validateToken(request.getToken())) {
//            throw new ServiceFaultException("ERROR", new ServiceFault("401", "Unauthorized"));
//        }
        try {
//            String username = tokenGenerator.decrypt(request.getToken());
            String username = request.getToken();
            List<Account> accounts = accountRepository.findByUser(username);
            for (Account account : accounts) {
                response.getAccount().add(Wrapper.wrapAccount(account));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }


}
