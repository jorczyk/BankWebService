package com.majorczyk.dao;

import com.majorczyk.model.Transfer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Piotr on 2018-01-12.
 */
@Repository
public interface TransferDao extends CrudRepository<Transfer, Long> {
}
