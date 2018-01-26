package com.majorczyk.database;

import com.majorczyk.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Interface extending spring MongoRepository to access accounts' repository
 */
public interface AccountRepository extends MongoRepository<Account, String> {

    /**
     * Finds bank account by it's number
     * @param number - account number
     * @return Bank account object
     */
    Account findByAccountNumber(String number);

    /**
     * Finds all user's bank accounts
     * @param user - user id
     * @return list of bank accounts owned by user
     */
    List<Account> findByUser(String user);

}
