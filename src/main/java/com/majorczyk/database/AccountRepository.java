package com.majorczyk.database;

import com.majorczyk.model.Account;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Piotr on 2018-01-15.
 */
public interface AccountRepository extends MongoRepository<Account, ObjectId> {

    Account findByAccountNumber(String name);
    Account findAccountByAccountNumberAndCredentials(String accountNumber, int credentials);

}
