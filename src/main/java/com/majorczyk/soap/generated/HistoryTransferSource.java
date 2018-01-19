
package com.majorczyk.soap.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HistoryTransferSource complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HistoryTransferSource">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="InnerTransferEnum" type="{com/majorczyk/soap/account}InnerTransferEnum"/>
 *         &lt;element name="accountFrom" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HistoryTransferSource", namespace = "com/majorczyk/soap/account", propOrder = {
    "innerTransferEnum",
    "accountFrom"
})
public class HistoryTransferSource {

    @XmlElement(name = "InnerTransferEnum", namespace = "com/majorczyk/soap/account")
    @XmlSchemaType(name = "string")
    protected InnerTransferEnum innerTransferEnum;
    @XmlElement(namespace = "com/majorczyk/soap/account")
    protected String accountFrom;

    /**
     * Gets the value of the innerTransferEnum property.
     * 
     * @return
     *     possible object is
     *     {@link InnerTransferEnum }
     *     
     */
    public InnerTransferEnum getInnerTransferEnum() {
        return innerTransferEnum;
    }

    /**
     * Sets the value of the innerTransferEnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link InnerTransferEnum }
     *     
     */
    public void setInnerTransferEnum(InnerTransferEnum value) {
        this.innerTransferEnum = value;
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

}
