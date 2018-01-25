package com.majorczyk.model;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * Bank model
 */

@NoArgsConstructor
public class Bank {

//    private static final String branchNumber = "00112245";
//    private static final String POLAND_CODE = "2521";

    /**
     * Database unique id
     */
    @Id
    public String Id;
    /**
     * Specific bank id for business logic purpose
     */
    public String bankId;
    /**
     * Bank URL address
     */
    public String url;

    public Bank(String bankId, String url) {
        this.bankId = bankId;
        this.url = url;
    }
}
