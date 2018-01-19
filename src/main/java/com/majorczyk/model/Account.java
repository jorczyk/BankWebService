package com.majorczyk.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import javax.persistence.*;

/**
 * Created by Piotr on 2018-01-11.
 */
@Entity
@Table(name = "accounts")
public class Account {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private ObjectId id;

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
    private int credentials;
}
