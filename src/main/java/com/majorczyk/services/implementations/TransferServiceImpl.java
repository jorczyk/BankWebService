package com.majorczyk.services.implementations;

import com.majorczyk.dao.TransferDao;
import com.majorczyk.model.Transfer;
import com.majorczyk.services.intefraces.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by Piotr on 2018-01-11.
 */
@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    TransferDao transferDao;

    @Override
    public void save(Transfer transfer) {
        transferDao.save(transfer);
    }
}
