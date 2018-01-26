package com.majorczyk.database;

import com.majorczyk.model.Operation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Interface extending spring MongoRepository to access operations' repository
 */
public interface OperationRepository extends MongoRepository<Operation, String> {

    /**
     * Finds operation by account's number
     * @param number - account's number
     * @return List of operations connected with account
     */
    List<Operation> findByAccountNo(String number);
}
