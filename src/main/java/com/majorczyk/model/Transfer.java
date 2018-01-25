package com.majorczyk.model;

import com.majorczyk.soap.generated.AccountHistoryEntity;
import com.majorczyk.soap.generated.HistoryTransferSource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Bank transfer model
 */

@NoArgsConstructor
@Entity
@Table(name = "transfers")
public class Transfer {

    /**
     * Database unique id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;//?

    /**
     * Transfer type
     */
    @Setter
    @Getter
    @Column
    private TransferType transferType;//?

    /**
     * Source account number
     */
    @Setter
    @Getter
    @Column
    private String sourceAccountNo;

    /**
     * Destinations account number
     */
    @Setter
    @Getter
    @Column
    private String destinationAccountNo;//?

    /**
     * Amount transferred
     */
    @Setter
    @Getter
    @Column
    private long amount;

    /**
     * Transfer title
     */
    @Setter
    @Getter
    @Column
    private String title;

    @Setter
    @Getter
    @Column
    private String name;

    /**
     * Balance at source account after transfer
     */
    @Setter
    @Getter
    @Column
    private int balance;

    public Transfer(TransferType transferType, String sourceAccountNo, String destinationAccountNo, long amount, String title, String name, int balance) {
        this.transferType = transferType;
        this.sourceAccountNo = sourceAccountNo;
        this.destinationAccountNo = destinationAccountNo;
        this.amount = amount;
        this.title = title;
        this.name = name;
        this.balance = balance;
    }

    public AccountHistoryEntity convertToResponse(){//TODO
        AccountHistoryEntity responseHistory = new AccountHistoryEntity();
        HistoryTransferSource source = new HistoryTransferSource();
        source.setAccountFrom(sourceAccountNo);
        responseHistory.setBalanceAfter(balance);
        responseHistory.setSource(source);
        responseHistory.setDestination(destinationAccountNo);
//        responseHistory.set(type.getDescription());
        return responseHistory;
    }

}
