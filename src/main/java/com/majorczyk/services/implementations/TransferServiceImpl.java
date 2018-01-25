package com.majorczyk.services.implementations;

import com.majorczyk.database.AccountRepository;
import com.majorczyk.database.TransferRepository;
import com.majorczyk.model.Account;
import com.majorczyk.model.Transfer;
import com.majorczyk.model.TransferType;
import com.majorczyk.services.intefraces.TransferService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

/**
 * Created by Piotr on 2018-01-11.
 */
@Service
public class TransferServiceImpl implements TransferService {

    private static final String FILENAME = "Banki.csv";

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Override
    public void save(Transfer transfer) {
        transferRepository.save(transfer);
    }

    @Override
    public List<Transfer> findByAccountNumbertoFrom(String accountNumber) {
        List<Transfer> to = transferRepository.findByDestinationAccountNumber(accountNumber);
        List<Transfer> from = transferRepository.findBySourceAccount(accountNumber);
        to.addAll(from);
        return to;
    }

    @Override
    public void saveInternalTransfer(String from, String to, int amount) {
        try {
            Account accountFrom = accountRepository.findByAccountNumber(from);
            Account accountTo = accountRepository.findByAccountNumber(to);
            validateTransfer(accountFrom, amount);
            accountFrom.setBalance(accountFrom.getBalance() - amount);
            accountTo.setBalance(accountTo.getBalance() + amount);
            accountRepository.save(accountFrom);
            accountRepository.save(accountTo);
            Transfer transfer = new Transfer();
            transfer.setTransferType(TransferType.INTERNAL_TRANSFER);
            transfer.setSourceAccountNo(accountFrom.getAccountNumber());
            transfer.setDestinationAccountNo(accountTo.getAccountNumber());
            transfer.setAmmount(amount);
            transfer.setBalance(accountFrom.getBalance());
            transferRepository.save(transfer);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void savePayment(String to, int amount) {
        try {
            Account accountTo = accountRepository.findByAccountNumber(to);
            if (amount < 0)
                throw new Exception();
            accountTo.setBalance(accountTo.getBalance() + amount);
            accountRepository.save(accountTo);
            Transfer transfer = new Transfer();
            transfer.setTransferType(TransferType.PAYMENT);
            transfer.setDestinationAccountNo(accountTo.getAccountNumber());
            transfer.setTitle("Payment");
            transfer.setAmmount(amount);
            transfer.setBalance(accountTo.getBalance());
            transferRepository.save(transfer);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void saveWithdrawal(String from, int amount) {
        try {
            Account accountFrom = accountRepository.findByAccountNumber(from);
            validateTransfer(accountFrom, amount);
            accountFrom.setBalance(accountFrom.getBalance() - amount);
            accountRepository.save(accountFrom);
            Transfer transfer = new Transfer();
            transfer.setTransferType(TransferType.WITHDRAWAL);
            transfer.setSourceAccountNo(accountFrom.getAccountNumber());
            transfer.setTitle("Withdrawal");
            transfer.setAmmount(amount);
            transfer.setBalance(accountFrom.getBalance());
            transferRepository.save(transfer);
            transferRepository.save(transfer);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void saveExternalTransfer(String from, String to, int amount, String name, String title) {
        try {
            Account accountFrom = accountRepository.findByAccountNumber(from);
            validateTransfer(accountFrom, amount);
            String url = readCsv(to);
            if (url.equals(""))
                throw new Exception();
            final String uri = url + "/accounts/" + to + "/history";
            JSONObject request = new JSONObject()
                    .put("source_account", from)
                    .put("amount", amount)
                    .put("title", title)
                    .put("name", name);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            RestTemplateBuilder builder = new RestTemplateBuilder();
            RestTemplate restTemplate = builder.basicAuthorization("admin", "admin").build();
            restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<String>(request.toString(), headers), ResponsePayload.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void validateTransfer(Account from, int amount) throws Exception {
        if(amount < 0)
            throw new Exception();
        if(from.getBalance() < amount)
            throw new Exception();
    }

    private String readCsv(String accountTo) {
        String findUrl = "";
        try {
            Reader in = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream("/" + FILENAME)));
            Iterable<CSVRecord> records = null;
            records = CSVFormat.RFC4180.withHeader("ID Banku", "URL", "przykładowe konto").parse(in);

            for (CSVRecord record : records) {

                String id = record.get("ID Banku");
                String url = record.get("URL");
                String account = record.get("konto");
                if (id.equals(accountTo.substring(2, 10)))
                    findUrl = url;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return findUrl;
    }

}



