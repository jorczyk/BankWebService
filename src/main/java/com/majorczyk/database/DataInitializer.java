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
        cleanDatabase(userRepository, accountRepository, banksRepository, operationRepository);

        userRepository.save(new User("user1", ValidationUtils.encryptPassword("user1")));
        accountRepository.save(new Account("23001122450000000000000000",0,"user1"));
        accountRepository.save(new Account("23001122450000000000000097",0, "user1"));

        getBanks();
        System.out.println("Done initializing database");

    }

    /**
     * Loads banks from csv file
     */
    private void getBanks() {
        try {
            Resource resource = resourceLoader.getResource("classpath:"+bankIdsFilename);
            InputStream inputStream = resource.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] values = line.split(",");
                if (values.length < 2 || !values[0].matches("^[0-9]*$")) {
                    line = bufferedReader.readLine();
                    continue;
                }
                Bank bank = new Bank(values[0], values[1]);
                banksRepository.save(bank);
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears database
     * @param userRepository
     * @param accountRepository
     * @param banksRepository
     * @param operationRepository
     */
    private static void cleanDatabase(UserRepository userRepository, AccountRepository accountRepository, BankRepository banksRepository, OperationRepository operationRepository) {
        userRepository.deleteAll();
        accountRepository.deleteAll();
        banksRepository.deleteAll();
        operationRepository.deleteAll();
    }
}
