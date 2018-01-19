
package com.majorczyk.soap.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InnerTransferEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="InnerTransferEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PAYMENT"/>
 *     &lt;enumeration value="WITHDRAWAL"/>
 *     &lt;enumeration value="BANK_FEE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "InnerTransferEnum", namespace = "com/majorczyk/soap/account")
@XmlEnum
public enum InnerTransferEnum {

    PAYMENT,
    WITHDRAWAL,
    BANK_FEE;

    public String value() {
        return name();
    }

    public static InnerTransferEnum fromValue(String v) {
        return valueOf(v);
    }

}
