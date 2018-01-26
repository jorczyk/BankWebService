package com.majorczyk.services.implementations;

import com.majorczyk.database.AccountRepository;
import com.majorczyk.database.BankRepository;
import com.majorczyk.database.OperationRepository;
import com.majorczyk.model.Account;
import com.majorczyk.model.Bank;
import com.majorczyk.model.Operation;
import com.majorczyk.services.intefraces.TransferService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.majorczyk.soap.generated.*;


/**
 * Service for bank operations
 */
@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private BankRepository bankRepository;

    /**
     * Validates if accont has sufficient funds for transfer to occur
     * @param account account object
     * @param transferRequest specific transfer to be made
     * @return error message
     */
    @Override
    public String validateAccount(Account account, Transfer transferRequest) {
        String result = "";
        if (account.getBalance() < transferRequest.getAmount()) {
            result = "Insufficient funds";
        }
        return result;
    }

    /**
     * Inte-bank transfer logic
     * @param request transfer to be made
     * @param name
     * @return
     * @throws IllegalStateException
     */
    @Override
    public ResponseEntity<String> sendInterbankTransfer(Transfer request, String name) throws IllegalStateException {
        String account = request.getAccountTo();
        String bankId = account.substring(2, 10);
        Bank bank = bankRepository.findByBankId(bankId);
        if (bank == null) {
            throw new IllegalStateException("Invalid receiver account number");
        }
        String url = bank.getUrl()+"/accounts/"+request.getAccountTo()+"/history";
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

    /**
     * Transfer logic and saving to database
     * @param request transfer to be made
     * @param account source account
     * @param interbank true if transfer is inter-bank
     */
    @Override
    public void saveTransfer(Transfer request, Account account, boolean interbank) {
        account.setBalance(account.getBalance()-request.getAmount());
        Operation operation = new Operation(account.getAccountNumber(), request.getTitle(), -request.getAmount(),
                request.getAccountTo(), account.getBalance());
        operationRepository.save(operation);
        accountRepository.save(account);
        if (!interbank) {
            String destAcc = request.getAccountTo();
            Account acc = accountRepository.findByAccountNumber(destAcc);
            acc.setBalance(acc.getBalance()+request.getAmount());
            Operation operation1 = new Operation(acc.getAccountNumber(), request.getTitle(), request.getAmount(),
                    request.getAccountFrom(), acc.getBalance());
            operationRepository.save(operation1);
            accountRepository.save(acc);
        }
    }
}



