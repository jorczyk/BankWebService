package com.majorczyk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Piotr on 2018-01-11.
 */
@AllArgsConstructor
public enum TransferType {
    EXTERNAL_TANSFER("ET","External transfer"),
    INTERNAL_TRANSFER("IT","Internal transfer"),
    WITHDRAWAL("W","Cash withdrawal from account"),
    PAYMENT("P","Payemnt made to account"),
    DEPOSIT("D","Deposit made to account");

    @Setter
    @Getter
    public String code;

    @Setter
    @Getter
    public String description;

    public static TransferType getByCode(String code) {
        for(TransferType e : values()) {
            if(e.code.equals(code))
                return e;
        }
        return null;
    }

}
