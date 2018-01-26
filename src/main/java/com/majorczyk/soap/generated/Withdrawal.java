
package com.majorczyk.soap.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="operationPayload" type="{com/majorczyk/soap/account}OperationPayload"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "operationPayload"
})
@XmlRootElement(name = "Withdrawal", namespace = "com/majorczyk/soap/account")
public class Withdrawal {

    @XmlElement(namespace = "com/majorczyk/soap/account", required = true)
    protected OperationPayload operationPayload;

    /**
     * Gets the value of the operationPayload property.
     * 
     * @return
     *     possible object is
     *     {@link OperationPayload }
     *     
     */
    public OperationPayload getOperationPayload() {
        return operationPayload;
    }

    /**
     * Sets the value of the operationPayload property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationPayload }
     *     
     */
    public void setOperationPayload(OperationPayload value) {
        this.operationPayload = value;
    }

}
