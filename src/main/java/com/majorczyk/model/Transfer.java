package com.majorczyk.model;

import com.majorczyk.soap.generated.AccountHistoryEntity;
import com.majorczyk.soap.generated.GetAccountHistoryResponse;
import com.majorczyk.soap.generated.HistoryTransferSource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import javax.persistence.*;

/**
 * Created by Piotr on 2018-01-11.
 */
@NoArgsConstructor
@Entity
@Table(name = "transfers")
public class Transfer {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private ObjectId id;

    @Setter
    @Getter
    @Column
    private TransferType transferType;

    @Setter
    @Getter
    @Column
    private String sourceAccount;

    @Setter
    @Getter
    @Column
    private String destinationAccountNumber;

    @Setter
    @Getter
    @Column
    private Integer ammount;

    @Setter
    @Getter
    @Column
    private String title;

    @Setter
    @Getter
    @Column
    private int balance;

    public AccountHistoryEntity convertToResponse(){
        AccountHistoryEntity responseHistory = new AccountHistoryEntity();
        HistoryTransferSource source = new HistoryTransferSource();
        source.setAccountFrom(sourceAccount);
        responseHistory.setBalanceAfter(balance);
        responseHistory.setSource(source);
        responseHistory.setDestination(destinationAccountNumber);
//        responseHistory.set(type.getDescription());
        return responseHistory;
    }

//    @Setter
//    @Getter
//    @Column
//    private String name;
}
