package com.majorczyk.services.intefraces;

import com.majorczyk.model.Account;
import com.majorczyk.model.Transfer;

import java.util.List;

/**
 * Created by Piotr on 2018-01-11.
 */
public interface AccountService {
    void save(Account account);
    Account findByAccountNumber(String accountNumber);
    List<Transfer> getAccountHistory(String accountNumber);


}
//    Account getAccount(String accountNumber, User user) throws AccountException;
//    List<Transfer> getAccountHistory(String accountNumber, User user) throws AccountException;
//    void createAccount(User user);