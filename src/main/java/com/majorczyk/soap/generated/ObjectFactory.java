
package com.majorczyk.soap.generated;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.majorczyk.soap.generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.majorczyk.soap.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Payment }
     * 
     */
    public Payment createPayment() {
        return new Payment();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link TokenResponse }
     * 
     */
    public TokenResponse createTokenResponse() {
        return new TokenResponse();
    }

    /**
     * Create an instance of {@link TransferResponse }
     * 
     */
    public TransferResponse createTransferResponse() {
        return new TransferResponse();
    }

    /**
     * Create an instance of {@link Withdrawal }
     * 
     */
    public Withdrawal createWithdrawal() {
        return new Withdrawal();
    }

    /**
     * Create an instance of {@link ExternalTransfer }
     * 
     */
    public ExternalTransfer createExternalTransfer() {
        return new ExternalTransfer();
    }

    /**
     * Create an instance of {@link Login }
     * 
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link GetAccountHistoryRequest }
     * 
     */
    public GetAccountHistoryRequest createGetAccountHistoryRequest() {
        return new GetAccountHistoryRequest();
    }

    /**
     * Create an instance of {@link GetAccountHistoryResponse }
     * 
     */
    public GetAccountHistoryResponse createGetAccountHistoryResponse() {
        return new GetAccountHistoryResponse();
    }

    /**
     * Create an instance of {@link AccountHistoryEntity }
     * 
     */
    public AccountHistoryEntity createAccountHistoryEntity() {
        return new AccountHistoryEntity();
    }

    /**
     * Create an instance of {@link InternalTransfer }
     * 
     */
    public InternalTransfer createInternalTransfer() {
        return new InternalTransfer();
    }

    /**
     * Create an instance of {@link HistoryTransferSource }
     * 
     */
    public HistoryTransferSource createHistoryTransferSource() {
        return new HistoryTransferSource();
    }

}
