
package com.majorczyk.soap.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransferForHistory complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransferForHistory">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="balance" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="accountFrom" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="accountTo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransferForHistory", namespace = "com/majorczyk/soap/account", propOrder = {
    "balance",
    "accountFrom",
    "accountTo",
    "type"
})
public class TransferForHistory {

    @XmlElement(namespace = "com/majorczyk/soap/account")
    protected long balance;
    @XmlElement(namespace = "com/majorczyk/soap/account", required = true)
    protected String accountFrom;
    @XmlElement(namespace = "com/majorczyk/soap/account", required = true)
    protected String accountTo;
    @XmlElement(namespace = "com/majorczyk/soap/account", required = true)
    protected String type;

    /**
     * Gets the value of the balance property.
     * 
     */
    public long getBalance() {
        return balance;
    }

    /**
     * Sets the value of the balance property.
     * 
     */
    public void setBalance(long value) {
        this.balance = value;
    }

    /**
     * Gets the value of the accountFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountFrom() {
        return accountFrom;
    }

    /**
     * Sets the value of the accountFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountFrom(String value) {
        this.accountFrom = value;
    }

    /**
     * Gets the value of the accountTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountTo() {
        return accountTo;
    }

    /**
     * Sets the value of the accountTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountTo(String value) {
        this.accountTo = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
