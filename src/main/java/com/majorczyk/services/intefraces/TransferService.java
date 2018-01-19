package com.majorczyk.services.intefraces;

        import com.majorczyk.model.Transfer;
        import com.majorczyk.model.User;

        import java.util.List;

/**
 * Created by Piotr on 2018-01-11.
 */
public interface TransferService {

    void save(Transfer transfer);
    List<Transfer> findByAccountNumbertoFrom(String accountNumber);
    void saveInternalTransfer(String from, String to, int amount);
    void savePayment(String to, int amount);
    void saveWithdrawal(String from, int amount);
    void saveExternalTransfer(String from, String to, int amount, String name, String title);
}

