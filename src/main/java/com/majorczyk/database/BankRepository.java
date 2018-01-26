package com.majorczyk.database;

import com.majorczyk.model.Bank;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface extending spring MongoRepository to access banks' repository
 */
public interface BankRepository extends MongoRepository<Bank, String> {
    /**
     * Finds bank by it's id
     * @param bankId - searched bank id
     * @return Bank object with given id
     */
    Bank findByBankId(String bankId);
}
