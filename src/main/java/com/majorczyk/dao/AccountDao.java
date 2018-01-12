package com.majorczyk.dao;

import com.majorczyk.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Piotr on 2018-01-12.
 */

@Repository
public interface AccountDao extends CrudRepository<Account, Long> {
    Account findAccountByAccountNumber(String accountNumber);
}
