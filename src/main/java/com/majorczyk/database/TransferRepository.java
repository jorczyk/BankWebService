package com.majorczyk.database;

import com.majorczyk.model.Account;
import com.majorczyk.model.Transfer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Piotr on 2018-01-15.
 */
public interface TransferRepository extends MongoRepository<Transfer, ObjectId> {
    List<Transfer> findBySourceAccount(String accountFrom);
    List<Transfer> findByDestinationAccountNumber(String accountFrom);
}
