package com.majorczyk.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Class for bank operations
 */
@NoArgsConstructor
public class Operation {

    /**
     * Database unique id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String id;

    /**
     * Account number
     */
    @Getter
    @Setter
    public String accountNo;

    /**
     * Operation title
     */
    @Getter
    @Setter
    public String title;

    /**
     * Amount of cash involved in operation
     */
    @Getter
    @Setter
    public long amount;

    /**
     * Source account number
     */
    @Getter
    @Setter
    public String source;

    /**
     * Balance of account after operation
     */
    @Getter
    @Setter
    public long balance;

    public Operation(String accountNo, String title, long amount, String source, long balance) {
        this.accountNo = accountNo;
        this.title = title;
        this.amount = amount;
        this.source = source;
        this.balance = balance;
    }
}
