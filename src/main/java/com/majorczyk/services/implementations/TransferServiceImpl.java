package com.majorczyk.services.implementations;

import com.majorczyk.database.AccountRepository;
import com.majorczyk.database.BankRepository;
import com.majorczyk.database.OperationRepository;
import com.majorczyk.model.Account;
//import com.majorczyk.model.Transfer;
import com.majorczyk.model.Bank;
import com.majorczyk.model.Operation;
import com.majorczyk.model.TransferType;
import com.majorczyk.services.intefraces.TransferService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import com.majorczyk.soap.generated.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

/**
 * Service for bank operations
 */
@Service
public class TransferServiceImpl implements TransferService {

    private static final String FILENAME = "Banki.csv";

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private BankRepository bankRepository;

//    private void validateTransfer(Account from, int amount) throws Exception {
//        if(amount < 0)
//            throw new Exception();
//        if(from.getBalance() < amount)
//            throw new Exception();
//    }

//    private String readCsv(String accountTo) {
//        String findUrl = "";
//        try {
//            Reader in = new BufferedReader(new InputStreamReader(
//                    this.getClass().getResourceAsStream("/" + FILENAME)));
//            Iterable<CSVRecord> records = null;
//            records = CSVFormat.RFC4180.withHeader("ID Banku", "URL", "przykładowe konto").parse(in);
//
//            for (CSVRecord record : records) {
//
//                String id = record.get("ID Banku");
//                String url = record.get("URL");
//                String account = record.get("konto");
//                if (id.equals(accountTo.substring(2, 10)))
//                    findUrl = url;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return findUrl;
//    }


    @Override
    public String validateAccount(Account account, Transfer transferRequest) {
        String result = "";
        if (account.getBalance() < transferRequest.getAmount()) {
            result = "Insufficient funds";
        }
        return result;
    }

    @Override
    public ResponseEntity<String> sendInterbankTransfer(Transfer request, String name) throws IllegalStateException {
        String account = request.getAccountTo();
        String bankId = account.substring(2, 10);
        Bank bank = bankRepository.findByBankId(bankId);
        if (bank == null) {
            throw new IllegalStateException("Niepoprawny numer konta odbiorcy.");
        }
        String url = bank.getUrl()+"/accounts/"+request.getAccountTo()+"/history";
//        String url ="http://localhost:8080/accounts/"+request.getDestinationAccount()+"/history";
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.basicAuthorization("admin", "admin").build();
        JSONObject json = new JSONObject();
        json.put("source_account", request.getAccountFrom());
        json.put("name", name);
        json.put("amount", request.getAmount());
        json.put("title", request.getTitle());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<JSONObject> entity = new HttpEntity<>(json, headers);
        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    @Override
    public void saveTransfer(Transfer request, Account account, boolean interbank) {
        account.setBalance(account.getBalance()-request.getAmount());
        Operation operation = new Operation(account.getAccountNumber(), request.getTitle(), -request.getAmount(), request.getAccountTo(), account.getBalance());
        operationRepository.save(operation);
        accountRepository.save(account);
        if (!interbank) {
            String destAcc = request.getAccountTo();
            Account acc = accountRepository.findByAccountNumber(destAcc);
            acc.setBalance(acc.getBalance()+request.getAmount());
            Operation operation1 = new Operation(acc.getAccountNumber(), request.getTitle(), request.getAmount(), request.getAccountFrom(), acc.getBalance());
            operationRepository.save(operation1);
            accountRepository.save(acc);
        }
    }
}



