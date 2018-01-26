package com.majorczyk.security;

import com.majorczyk.model.Transfer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigInteger;

/**
 * Util class used for validation purposes
 */
public class ValidationUtils {

    /**
     * Validates string length
     * @param str string to be checked
     * @param length maximum accepted length
     * @return boolean if valid
     */
    private static boolean validateStringLength(String str, int length) {
        return str.length() > 0 && str.length() <= length;
    }

    /**
     * Validates account number
     * @param accountNo account number
     * @return boolean if valid
     */
    public static boolean validateAccountNo(String accountNo) {
        accountNo = accountNo.replaceAll("\\s+","");
        if (accountNo.length() != 26) {
            return false;
        }
        String temp = accountNo.substring(2);
        String validationNumber = temp + "2521" + accountNo.substring(0, 2);
        BigInteger result = new BigInteger(validationNumber).remainder(new BigInteger("97"));
        return result.compareTo(new BigInteger("1")) == 0;
    }

    /**
     * Validates transfer
     * @param transfer transfer object
     * @param accountNo account number
     * @return error field
     */
    public static String validateTransfer(Transfer transfer, String accountNo) {
        if (!validateAccountNo(transfer.getSourceAccountNo()) || accountNo.equals(transfer.getSourceAccountNo())) {
            return "source_account";
        }
        if (!validateStringLength(transfer.getTitle(), 255)) {
            return "title";
        }
        if (!validateStringLength(transfer.getName(), 255)) {
            return "name";
        }
        if (transfer.getAmount() <= 0) {
            return "amount";
        }
        return "";
    }

    /**
     * Password encryption
     * @param password
     * @return
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

    /**
     * password validation
     * @param dbPassword
     * @param userPassword
     * @return
     */
    public static boolean validatePassword(String dbPassword, String userPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(userPassword, dbPassword);
    }

    /**
     * Gets error message for error field
     * @param field error field
     * @return error message
     */
    public static String getErrorMessage(String field){
        switch (field) {
            case "source_account":
                return "Faulty account number";
            case "title":
                return "Transfer title empty or too long";
            case "name":
                return "No transfer source name or name too long";
            case "amount":
                return "Invalid transfer sum";
            default:
                return "";
        }
    }
}
