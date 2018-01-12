package com.majorczyk.services.implementations;

import com.majorczyk.dao.AccountDao;
import com.majorczyk.model.Account;
import com.majorczyk.services.intefraces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by Piotr on 2018-01-11.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public void save(Account account) {
        accountDao.save(account);
    }

    @Override
    public Account getAccountByNumber(String accountNumber) {
        return accountDao.findAccountByAccountNumber(accountNumber);
    }
}
