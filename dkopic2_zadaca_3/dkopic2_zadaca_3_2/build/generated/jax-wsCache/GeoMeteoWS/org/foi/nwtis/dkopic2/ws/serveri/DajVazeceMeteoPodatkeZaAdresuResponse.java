
package org.foi.nwtis.dkopic2.ws.serveri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dajVazeceMeteoPodatkeZaAdresuResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dajVazeceMeteoPodatkeZaAdresuResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="return" type="{http://serveri.ws.dkopic2.nwtis.foi.org/}meteoPodaci" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dajVazeceMeteoPodatkeZaAdresuResponse", propOrder = {
    "_return"
})
public class DajVazeceMeteoPodatkeZaAdresuResponse {

    @XmlElement(name = "return")
    protected MeteoPodaci _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link MeteoPodaci }
     *     
     */
    public MeteoPodaci getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link MeteoPodaci }
     *     
     */
    public void setReturn(MeteoPodaci value) {
        this._return = value;
    }

}
