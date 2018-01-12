package com.majorczyk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Piotr on 2018-01-11.
 */
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Getter
    private TransferType transferType;

    @Setter
    @Getter
    private String sourceAccount;

    @Setter
    @Getter
    private String destinationAccountNumber;

    @Setter
    @Getter
    private Integer ammount;

    @Setter
    @Getter
    private String title;

    @Setter
    @Getter
    private String name;
}
