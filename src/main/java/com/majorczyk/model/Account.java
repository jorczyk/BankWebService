package com.majorczyk.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import javax.persistence.*;

/**
 * Bank account model
 */

@Entity
@Table(name = "accounts")
public class Account {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Setter
    @Getter
    @Column(unique = true)
    private String accountNumber;

    @Setter
    @Getter
    @Column
    private Integer balance;

    @Setter
    @Getter
    @Column(unique = true)
    private String user;

    public Account(String accountNumber, Integer balance, String user) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.user = user;
    }
}
