package com.majorczyk.utils;


import com.majorczyk.model.Account;
import com.majorczyk.model.Operation;
import com.majorczyk.soap.generated.AccountHistoryEntity;

/**
 * Util for conversion between xsd generated class and model class
 */
public class Wrapper {
    /**
     * Converts model package class to xsd generated class
     * @param operation bank operation from model
     * @return SOAP object
     */
    public static AccountHistoryEntity wrapOperation(Operation operation) {
        AccountHistoryEntity soapOperation = new AccountHistoryEntity();
        soapOperation.setAmount(operation.getAmount());
        soapOperation.setBalanceAfter(operation.getBalance());
        soapOperation.setSource(operation.getSource());
        soapOperation.setTitle(operation.getTitle());
        return soapOperation;
    }
    /**
     * Converts model package class to xsd generated class
     * @param account bank account from model
     * @return SOAP object
     */
    public static com.majorczyk.soap.generated.Account wrapAccount(Account account) {
        com.majorczyk.soap.generated.Account soapAccount = new com.majorczyk.soap.generated.Account();
        soapAccount.setNumber(account.getAccountNumber());
        soapAccount.setBalance(account.getBalance());
        soapAccount.setUser(account.getUser());
        return soapAccount;
    }

}
