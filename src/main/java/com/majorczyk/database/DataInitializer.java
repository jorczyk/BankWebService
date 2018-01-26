package com.majorczyk.database;

import com.majorczyk.model.*;
import com.majorczyk.security.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Initializing database with sample users, accounts, banks, operations history
 */
@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private BankRepository banksRepository;
    @Autowired
    private ResourceLoader resourceLoader;
    @Value("${interbank.filename}")
    private String bankIdsFilename;

    /**
     * Invoke initializer
     * @param args
     */
    public void run(ApplicationArguments args) {
        userRepository.deleteAll();
        userRepository.save(new User("user1", ValidationUtils.encryptPassword("user1")));
        accountRepository.deleteAll();
        accountRepository.save(new Account("23001122450000000000000000",0,"user1"));
        accountRepository.save(new Account("23001122450000000000000097",0, "user1"));
        operationRepository.deleteAll();
//        operationRepository.save(new Operation("24001172750000000000000001", "Transfer", 10000, "23001122450000000000000000", 10000));
//        operationRepository.save(new Operation("94001172750000000000000002", "Deposit", 1111, TransferType.DEPOSIT.getDescription(), 11111));
//        operationRepository.save(new Operation("67001172750000000000000003", "Payment", -500, TransferType.PAYMENT.getDescription(), 200000));
//        operationRepository.save(new Operation("40001172750000000000000004", "Withdrawal", -5000, TransferType.WITHDRAWAL.getDescription(), 100));
        banksRepository.deleteAll();
        try {
            Resource resource = resourceLoader.getResource("classpath:"+bankIdsFilename);
            InputStream is = resource.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(is);
            BufferedReader bufferreader = new BufferedReader(inputStreamReader);
            String line = bufferreader.readLine();
            while (line != null) {
                String[] values = line.split(",");
                if (values.length < 2 || !values[0].matches("^[0-9]*$")) {
                    line = bufferreader.readLine();
                    continue;
                }
                Bank bank = new Bank(values[0], values[1]);
                banksRepository.save(bank);
                line = bufferreader.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("DONE initializing database");

    }
}
