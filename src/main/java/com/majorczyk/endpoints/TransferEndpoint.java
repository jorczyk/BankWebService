package com.majorczyk.endpoints;

import com.majorczyk.database.AccountRepository;
import com.majorczyk.database.BankRepository;
import com.majorczyk.database.OperationRepository;
import com.majorczyk.exceptions.ServiceFault;
import com.majorczyk.exceptions.ServiceFaultException;
import com.majorczyk.model.Account;
import com.majorczyk.model.Operation;
import com.majorczyk.model.TransferType;
import com.majorczyk.security.TokenGenerator;
import com.majorczyk.services.intefraces.TransferService;
import com.majorczyk.soap.generated.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

/**
 * API endpoint for transfers
 */
@Endpoint
public class TransferEndpoint {

    private final String NAMESPACE = "com/majorczyk/soap/account";

    @Autowired
    private TransferService transferService;
    @Autowired
    private TokenGenerator tokenGenerator ;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    BankRepository bankRepository;

    /**
     * Proceeds transfer request
     * @param request transfer request object
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE,
            localPart = "Transfer")
    @ResponsePayload
    public TransferResponse transfer(@RequestPayload Transfer request) {
        TransferResponse response = new TransferResponse();
//        if (!tokenGenerator.validateToken(request.getToken())) {
//            throw new ServiceFaultException("ERROR", new ServiceFault("401", "Unauthorized"));
//        }
//        if (request.getAmount() <= 0){
//            throw new ServiceFaultException("ERROR", new ServiceFault("500", "Amount cannot be lower than 0"));
//        }
        try {
//            String username = tokenGenerator.decrypt(request.getToken());
            String username = request.getToken();
            List<Account> accountList = accountRepository.findByUser(username);
            Account account = null;
            for (Account accountTemp : accountList) {
                if (accountTemp.getAccountNumber().equals(request.getAccountFrom())) {
                    account = accountTemp;
                    break;
                }
            }
            if (account == null || request.getAmount() <= 0 || request.getTitle().length() == 0 ||
                    request.getAccountTo().length() == 0 || request.getAccountFrom().equals(request.getAccountTo())) {
                throw new ServiceFaultException("ERROR", new ServiceFault("400", "Bad Request"));
            }
            OperationResponse responseTemp = new OperationResponse();
            String result = transferService.validateAccount(account, request);
            if (request.isInterbank()) {
                if (result.length() > 0) {
                    responseTemp.setStatus(-1);
                    responseTemp.setMessage(result);
                } else {
                    try {
                        ResponseEntity<String> restResponse = transferService.sendInterbankTransfer(request, username);
                        if (restResponse.getStatusCode() == HttpStatus.CREATED) {
                            transferService.saveTransfer(request, account, true);
                            responseTemp.setStatus(0);
                        } else {
                            responseTemp.setStatus(-1);
                            String body = restResponse.getBody();
                            System.out.println(body);
                            responseTemp.setMessage("External transfer refused");
                        }
                    } catch (HttpClientErrorException e) {
                        e.printStackTrace();
                        responseTemp.setStatus(-1);
                        responseTemp.setMessage("Receiver's data validation error");
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                        responseTemp.setStatus(-1);
                        responseTemp.setMessage("Faulty destination account number");
                    } catch (Exception e) {
                        e.printStackTrace();
                        responseTemp.setStatus(-1);
                        responseTemp.setMessage("Receiver's internal error");
                    }
                }

            } else {
                if (result.length() > 0) {
                    responseTemp.setStatus(-1);
                    responseTemp.setMessage(result);
                } else {
                    Account dest = accountRepository.findByAccountNumber(request.getAccountTo());
                    if (dest == null) {
                        responseTemp.setStatus(-1);
                        responseTemp.setMessage("Destination account doesn't exist");
                    } else {
                        transferService.saveTransfer(request, account, false);
                        responseTemp.setStatus(0);
                    }
                }

            }
            response.setResponse(responseTemp);
        }catch (Exception e){
            throw e;
        }
        return response;
    }

    /**
     * Proceeds deposit request
     * @param request deposit request object
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE,
            localPart = "Deposit")
    @ResponsePayload
    public DepositResponse deposit(@RequestPayload Deposit request) {
        DepositResponse response = new DepositResponse();
        OperationPayload operationPayload = request.getOperationPayload();
//        if (!tokenGenerator.validateToken(operationPayload.getToken())) {
//            throw new ServiceFaultException("ERROR", new ServiceFault("401", "Unauthorized"));
//        }
        try {
//            String username = tokenGenerator.decrypt(operationPayload.getToken());
            String username = request.getOperationPayload().getToken();
            List<Account> accountList = accountRepository.findByUser(username);
            Account account = null;
            for (Account accountTemp : accountList) {
                if (accountTemp.getAccountNumber().equals(operationPayload.getAccountNo())) {
                    account = accountTemp;
                    break;
                }
            }
            if (account == null || operationPayload.getAmount() <= 0 || operationPayload.getTitle().length() == 0) {
                throw new ServiceFaultException("ERROR", new ServiceFault("400", "Bad Request"));
            }
            account.setBalance(account.getBalance()+operationPayload.getAmount());
            Operation operation = new Operation(account.getAccountNumber(), operationPayload.getTitle(),
                    operationPayload.getAmount(), TransferType.DEPOSIT.getDescription(), account.getBalance());
            operationRepository.save(operation);
            accountRepository.save(account);
            OperationResponse responseTemp = new OperationResponse();
            responseTemp.setStatus(0);
            response.setResponse(responseTemp);
        }catch (Exception e){
            throw e;
        }
        return response;
    }

    /**
     * Proceeds payment request
     * @param request payment request object
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE,
            localPart = "Payment")
    @ResponsePayload
    public PaymentResponse payment(@RequestPayload Payment request) {
        PaymentResponse response = new PaymentResponse();
        OperationPayload operationPayload = request.getOperationPayload();
        if (!tokenGenerator.validateToken(operationPayload.getToken())) {
            throw new ServiceFaultException("ERROR", new ServiceFault("401", "Unauthorized"));
        }
        try {
            String username = tokenGenerator.decrypt(operationPayload.getToken());
            List<Account> accountList = accountRepository.findByUser(username);
            Account account = null;
            for (Account accountTemp : accountList) {
                if (accountTemp.getAccountNumber().equals(operationPayload.getAccountNo())) {
                    account = accountTemp;
                    break;
                }
            }
            if (account == null || operationPayload.getAmount() <= 0 || operationPayload.getTitle().length() == 0) {
                throw new ServiceFaultException("ERROR", new ServiceFault("400", "Bad Request"));
            }
            OperationResponse responseTemp = new OperationResponse();
            if (account.getBalance() < operationPayload.getAmount()) {
                responseTemp.setStatus(-1);
                responseTemp.setMessage("Insufficient funds");
            } else {
                account.setBalance(account.getBalance()-operationPayload.getAmount());
                Operation operation = new Operation(account.getAccountNumber(), operationPayload.getTitle(),
                        -operationPayload.getAmount(), TransferType.PAYMENT.getDescription(), account.getBalance());
                operationRepository.save(operation);
                accountRepository.save(account);
                responseTemp.setStatus(0);
            }
            response.setResponse(responseTemp);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    /**
     * Proceeds withdrawal request
     * @param request withdrawal request object
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE,
            localPart = "Withdrawal")
    @ResponsePayload
    public WithdrawalResponse withdrawal(@RequestPayload Withdrawal request) {
        WithdrawalResponse response = new WithdrawalResponse();
        OperationPayload operationPayload = request.getOperationPayload();
//        if (!tokenGenerator.validateToken(operationPayload.getToken())) {
//            throw new ServiceFaultException("ERROR", new ServiceFault("401", "Unauthorized"));
//        }
        try {
//            String username = tokenGenerator.decrypt(operationPayload.getToken());
            String username = request.getOperationPayload().getToken();
            List<Account> accountList = accountRepository.findByUser(username);
            Account account = null;
            for (Account accountTemp : accountList) {
                if (accountTemp.getAccountNumber().equals(operationPayload.getAccountNo())) {
                    account = accountTemp;
                    break;
                }
            }
            if (account == null || operationPayload.getAmount() <= 0 || operationPayload.getTitle().length() == 0) {
                throw new ServiceFaultException("ERROR", new ServiceFault("400", "Bad Request"));
            }
            OperationResponse responseTemp = new OperationResponse();
            if (account.getBalance() < operationPayload.getAmount()) {
                responseTemp.setStatus(-1);
                responseTemp.setMessage("Insufficient funds");
            } else {
                account.setBalance(account.getBalance()-operationPayload.getAmount());
                Operation operation = new Operation(account.getAccountNumber(), operationPayload.getTitle(),
                        -operationPayload.getAmount(), TransferType.WITHDRAWAL.getDescription(), account.getBalance());
                operationRepository.save(operation);
                accountRepository.save(account);
                responseTemp.setStatus(0);
            }
            response.setResponse(responseTemp);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
}