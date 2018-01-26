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
 * Created by Piotr on 2018-01-19.
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

    @PayloadRoot(namespace = NAMESPACE,
            localPart = "Transfer")
    @ResponsePayload
    public TransferResponse transfer(@RequestPayload Transfer request) {
        TransferResponse response = new TransferResponse();
        if (!tokenGenerator.validateToken(request.getToken())) {
            throw new ServiceFaultException("ERROR", new ServiceFault("401", "Unauthorized"));
        }
        try {
            String username = tokenGenerator.decrypt(request.getToken());
            List<Account> accounts = accountRepository.findByUser(username);
            Account account = null;
            for (Account acc : accounts) {
                if (acc.getAccountNumber().equals(request.getAccountFrom())) {
                    account = acc;
                    break;
                }
            }
            if (account == null || request.getAmount() <= 0 || request.getTitle().length() == 0 ||
                    request.getAccountTo().length() == 0 || request.getAccountFrom().equals(request.getAccountTo())) {
                throw new ServiceFaultException("ERROR", new ServiceFault("400", "Bad Request"));
            }
            OperationResponse res = new OperationResponse();
            String result = transferService.validateAccount(account, request);
            if (request.isInterbank()) {
                if (result.length() > 0) {
                    res.setStatus(-1);
                    res.setMessage(result);
                } else {
                    try {
                        ResponseEntity<String> restResponse = transferService.sendInterbankTransfer(request, username);
                        if (restResponse.getStatusCode() == HttpStatus.CREATED) {
                            transferService.saveTransfer(request, account, true);
                            res.setStatus(0);
                        } else {
                            res.setStatus(-1);
                            String body = restResponse.getBody();
                            System.out.println(body);
                            res.setMessage("Bank zewnetrzny odrzucil przelew.");
                        }
                    } catch (HttpClientErrorException e) {
                        e.printStackTrace();
                        res.setStatus(-1);
                        res.setMessage("Blad walidacji danych po stronie odbiorcy.");
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                        res.setStatus(-1);
                        res.setMessage("Niepoprawny numer konta odbiorcy.");
                    } catch (Exception e) {
                        e.printStackTrace();
                        res.setStatus(-1);
                        res.setMessage("Blad wewnetrzny po stronie odbiorcy.");
                    }
                }

            } else {
                if (result.length() > 0) {
                    res.setStatus(-1);
                    res.setMessage(result);
                } else {
                    Account dest = accountRepository.findByAccountNumber(request.getAccountTo());
                    if (dest == null) {
                        res.setStatus(-1);
                        res.setMessage("Konto odbiorcy nie istnieje.");
                    } else {
                        transferService.saveTransfer(request, account, false);
                        res.setStatus(0);
                    }
                }

            }
            response.setResponse(res);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE,
            localPart = "Deposit")
    @ResponsePayload
    public DepositResponse deposit(@RequestPayload Deposit request) {
        DepositResponse response = new DepositResponse();
        OperationPayload operationPayload = request.getOperationPayload();
        if (!tokenGenerator.validateToken(operationPayload.getToken())) {
            throw new ServiceFaultException("ERROR", new ServiceFault("401", "Unauthorized"));
        }
        try {
            String username = tokenGenerator.decrypt(operationPayload.getToken());
            List<Account> accounts = accountRepository.findByUser(username);
            Account account = null;
            for (Account acc : accounts) {
                if (acc.getAccountNumber().equals(operationPayload.getAccountNo())) {
                    account = acc;
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
            OperationResponse res = new OperationResponse();
            res.setStatus(0);
            response.setResponse(res);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

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
            List<Account> accounts = accountRepository.findByUser(username);
            Account account = null;
            for (Account acc : accounts) {
                if (acc.getAccountNumber().equals(operationPayload.getAccountNo())) {
                    account = acc;
                    break;
                }
            }
            if (account == null || operationPayload.getAmount() <= 0 || operationPayload.getTitle().length() == 0) {
                throw new ServiceFaultException("ERROR", new ServiceFault("400", "Bad Request"));
            }
            OperationResponse res = new OperationResponse();
            if (account.getBalance() < operationPayload.getAmount()) {
                res.setStatus(-1);
                res.setMessage("Niewystarczajace srodki");
            } else {
                account.setBalance(account.getBalance()-operationPayload.getAmount());
                Operation operation = new Operation(account.getAccountNumber(), operationPayload.getTitle(),
                        -operationPayload.getAmount(), TransferType.PAYMENT.getDescription(), account.getBalance());
                operationRepository.save(operation);
                accountRepository.save(account);
                res.setStatus(0);
            }
            response.setResponse(res);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE,
            localPart = "Withdrawal")
    @ResponsePayload
    public WithdrawalResponse withdrawal(@RequestPayload Withdrawal request) {
        WithdrawalResponse response = new WithdrawalResponse();
        OperationPayload operationPayload = request.getOperationPayload();
        if (!tokenGenerator.validateToken(operationPayload.getToken())) {
            throw new ServiceFaultException("ERROR", new ServiceFault("401", "Unauthorized"));
        }
        try {
            String username = tokenGenerator.decrypt(operationPayload.getToken());
            List<Account> accounts = accountRepository.findByUser(username);
            Account account = null;
            for (Account acc : accounts) {
                if (acc.getAccountNumber().equals(operationPayload.getAccountNo())) {
                    account = acc;
                    break;
                }
            }
            if (account == null || operationPayload.getAmount() <= 0 || operationPayload.getTitle().length() == 0) {
                throw new ServiceFaultException("ERROR", new ServiceFault("400", "Bad Request"));
            }
            OperationResponse res = new OperationResponse();
            if (account.getBalance() < operationPayload.getAmount()) {
                res.setStatus(-1);
                res.setMessage("Niewystarczajace srodki");
            } else {
                account.setBalance(account.getBalance()-operationPayload.getAmount());
                Operation operation = new Operation(account.getAccountNumber(), operationPayload.getTitle(),
                        -operationPayload.getAmount(), TransferType.WITHDRAWAL.getDescription(), account.getBalance());
                operationRepository.save(operation);
                accountRepository.save(account);
                res.setStatus(0);
            }
            response.setResponse(res);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
}