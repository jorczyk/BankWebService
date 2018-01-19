
package com.majorczyk.soap.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccountHistoryEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountHistoryEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="source" type="{com/majorczyk/soap/account}HistoryTransferSource"/>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="destination" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="balanceAfter" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountHistoryEntity", namespace = "com/majorczyk/soap/account", propOrder = {
    "source",
    "amount",
    "title",
    "destination",
    "balanceAfter"
})
public class AccountHistoryEntity {

    @XmlElement(namespace = "com/majorczyk/soap/account", required = true)
    protected HistoryTransferSource source;
    @XmlElement(namespace = "com/majorczyk/soap/account")
    protected int amount;
    @XmlElement(namespace = "com/majorczyk/soap/account", required = true)
    protected String title;
    @XmlElement(namespace = "com/majorczyk/soap/account", required = true)
    protected String destination;
    @XmlElement(namespace = "com/majorczyk/soap/account")
    protected int balanceAfter;

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link HistoryTransferSource }
     *     
     */
    public HistoryTransferSource getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link HistoryTransferSource }
     *     
     */
    public void setSource(HistoryTransferSource value) {
        this.source = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     */
    public void setAmount(int value) {
        this.amount = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the destination property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the value of the destination property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestination(String value) {
        this.destination = value;
    }

    /**
     * Gets the value of the balanceAfter property.
     * 
     */
    public int getBalanceAfter() {
        return balanceAfter;
    }

    /**
     * Sets the value of the balanceAfter property.
     * 
     */
    public void setBalanceAfter(int value) {
        this.balanceAfter = value;
    }

}
