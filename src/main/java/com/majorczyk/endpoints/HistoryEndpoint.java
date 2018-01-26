package com.majorczyk.endpoints;

import com.majorczyk.database.OperationRepository;
import com.majorczyk.exceptions.ServiceFault;
import com.majorczyk.exceptions.ServiceFaultException;
import com.majorczyk.model.Operation;
import com.majorczyk.security.TokenGenerator;
import com.majorczyk.soap.generated.GetAccountHistoryRequest;
import com.majorczyk.soap.generated.GetAccountHistoryResponse;
import com.majorczyk.utils.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

/**
 * API endpoint for accounts' history
 */
@Endpoint
public class HistoryEndpoint {

    @Autowired
    TokenGenerator tokenGenerator ;
    @Autowired
    OperationRepository operationRepository;

    private final String NAMESPACE = "com/majorczyk/soap/account";

    /**
     * Gets account history
     * @param request - request payload containing account number and token
     * @return account's history
     */
    @PayloadRoot(namespace = NAMESPACE, localPart = "GetAccountHistoryRequest")
    @ResponsePayload
    public GetAccountHistoryResponse getHistory(@RequestPayload GetAccountHistoryRequest request) {
        GetAccountHistoryResponse response = new GetAccountHistoryResponse();
//        if (!tokenGenerator.validateToken(request.getToken())) {
//            throw new ServiceFaultException("ERROR", new ServiceFault("401", "Unauthorized"));
//        }
        List<Operation> operations = operationRepository.findByAccountNo(request.getAccountNo());
        for (Operation operation : operations) {
            response.getAccountHistory().add(Wrapper.wrapOperation(operation));
        }
        return response;
    }
}
