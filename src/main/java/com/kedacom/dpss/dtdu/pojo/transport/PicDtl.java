//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.12.26 at 03:07:18 ���� CST 
//


package com.kedacom.dpss.dtdu.pojo.transport;

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
 *         &lt;element name="IsFtrPic" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;sequence>
 *           &lt;element name="PicUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *           &lt;element name="PicStreamId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;element name="FtrCoord" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TrgtCoord" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "isFtrPic",
    "picUrl",
    "picStreamId",
    "ftrCoord",
    "trgtCoord"
})
@XmlRootElement(name = "PicDtl")
public class PicDtl {

    @XmlElement(name = "IsFtrPic")
    protected boolean isFtrPic;
    @XmlElement(name = "PicUrl")
    protected String picUrl;
    @XmlElement(name = "PicStreamId")
    protected String picStreamId;
    @XmlElement(name = "FtrCoord")
    protected String ftrCoord;
    @XmlElement(name = "TrgtCoord")
    protected String trgtCoord;

    /**
     * Gets the value of the isFtrPic property.
     * 
     */
    public boolean isIsFtrPic() {
        return isFtrPic;
    }

    /**
     * Sets the value of the isFtrPic property.
     * 
     */
    public void setIsFtrPic(boolean value) {
        this.isFtrPic = value;
    }

    /**
     * Gets the value of the picUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPicUrl() {
        return picUrl;
    }

    /**
     * Sets the value of the picUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPicUrl(String value) {
        this.picUrl = value;
    }

    /**
     * Gets the value of the picStreamId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPicStreamId() {
        return picStreamId;
    }

    /**
     * Sets the value of the picStreamId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPicStreamId(String value) {
        this.picStreamId = value;
    }

    /**
     * Gets the value of the ftrCoord property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFtrCoord() {
        return ftrCoord;
    }

    /**
     * Sets the value of the ftrCoord property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFtrCoord(String value) {
        this.ftrCoord = value;
    }

    /**
     * Gets the value of the trgtCoord property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrgtCoord() {
        return trgtCoord;
    }

    /**
     * Sets the value of the trgtCoord property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrgtCoord(String value) {
        this.trgtCoord = value;
    }

}