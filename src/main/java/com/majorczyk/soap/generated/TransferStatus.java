
package com.majorczyk.soap.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransferStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TransferStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="ERROR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TransferStatus", namespace = "com/majorczyk/soap/account")
@XmlEnum
public enum TransferStatus {

    OK,
    ERROR;

    public String value() {
        return name();
    }

    public static TransferStatus fromValue(String v) {
        return valueOf(v);
    }

}
