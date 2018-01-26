package com.majorczyk.services.intefraces;

import com.majorczyk.model.Account;
//import com.majorczyk.model.Transfer;
import com.majorczyk.soap.generated.Transfer;
import org.springframework.http.ResponseEntity;

/**
 * Created by Piotr on 2018-01-11.
 */
public interface TransferService {

    String validateAccount(Account account, Transfer transferRequest);
    ResponseEntity<String> sendInterbankTransfer(Transfer request, String name) throws IllegalStateException;
    void saveTransfer(Transfer request, Account account, boolean interbank);
//    void save(Transfer transfer);
//    List<Transfer> findByAccountNumbertoFrom(String accountNumber);
//    void saveInternalTransfer(String from, String to, int amount);
//    void savePayment(String to, int amount);
//    void saveWithdrawal(String from, int amount);
//    void saveExternalTransfer(String from, String to, int amount, String name, String title);
}

