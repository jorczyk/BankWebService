package com.majorczyk.security;

import com.majorczyk.model.Transfer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigInteger;

/**
 * Util class used for validation purposes
 */
public class ValidationUtils {

    private static boolean validateStringLength(String str, int length) {
        return str.length() > 0 && str.length() <= length;
    }

    public static boolean validateAccountNo(String accountNo) {
        accountNo = accountNo.replaceAll("\\s+","");
        if (accountNo.length() != 26) {
            return false;
        }
        String tempAcc = accountNo.substring(2);
        String validationNumber = tempAcc + "2521" + accountNo.substring(0, 2);
        BigInteger result = new BigInteger(validationNumber).remainder(new BigInteger("97"));
        return result.compareTo(new BigInteger("1")) == 0;
    }

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

    public static String encryptPassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

    public static boolean validatePassword(String dbPassword, String userPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(userPassword, dbPassword);
    }

    public static String getErrorMessage(String field){
        switch (field) {
            case "source_account":
                return "January";
            case "title":
                return "January";
            case "name":
                return "January";
            case "amount":
                return "January";
            default:
                return "";
        }
    }
}
