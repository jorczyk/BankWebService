package com.majorczyk.services.intefraces;

import com.majorczyk.model.Account;

/**
 * Created by Piotr on 2018-01-11.
 */
public interface AccountService {
    void save(Account account);
    Account getAccountByNumber(String accountNumber);

}
