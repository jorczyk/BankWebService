package com.majorczyk.services.implementations;

import com.majorczyk.database.AccountRepository;
import com.majorczyk.database.TransferRepository;
import com.majorczyk.model.Account;
import com.majorczyk.model.Transfer;
import com.majorczyk.services.intefraces.AccountService;
import com.majorczyk.services.intefraces.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Piotr on 2018-01-11.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

//    @Autowired
//    private TransferRepository transferRepository;

    @Autowired
    private TransferService transferService;

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public List<Transfer> getAccountHistory(String accountNumber) {
        Account account = findByAccountNumber(accountNumber);
        return transferService.findByAccountNumbertoFrom(accountNumber);
    }
}
