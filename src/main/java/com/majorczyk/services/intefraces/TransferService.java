package com.majorczyk.services.intefraces;

import com.majorczyk.model.Account;
//import com.majorczyk.model.Transfer;
import com.majorczyk.soap.generated.Transfer;
import org.springframework.http.ResponseEntity;

/**
 * Transfer service API interface
 */
public interface TransferService {

    String validateAccount(Account account, Transfer transferRequest);
    ResponseEntity<String> sendInterbankTransfer(Transfer request, String name) throws IllegalStateException;
    void saveTransfer(Transfer request, Account account, boolean interbank);
}

