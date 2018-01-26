package com.majorczyk.utils;


import com.majorczyk.model.Account;
import com.majorczyk.model.Operation;
import com.majorczyk.soap.generated.AccountHistoryEntity;

/**
 * Klasa pomocnicza konwertujaca obiekt modelu do obiektu soap
 */
public class Wrapper {
    /**
     * Funkcja konwertujaca obiekt modelu operacji bankowej do obiektu SOAP odbieranego przez serwer
     * @param operation obiekt modelu operacji bankowej
     * @return obiekt SOAP operacji bankowej
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
     * Funkcja konwertujaca obiekt modelu konta bankowego do obiektu SOAP odbieranego przez serwer
     * @param account obiekt modelu konta bankowego
     * @return obiekt SOAP konta bankowego
     */
    public static com.majorczyk.soap.generated.Account wrapAccount(Account account) {
        com.majorczyk.soap.generated.Account soapAccount = new com.majorczyk.soap.generated.Account();
        soapAccount.setNumber(account.getAccountNumber());
        soapAccount.setBalance(account.getBalance());
        soapAccount.setUser(account.getUser());
        return soapAccount;
    }

}
