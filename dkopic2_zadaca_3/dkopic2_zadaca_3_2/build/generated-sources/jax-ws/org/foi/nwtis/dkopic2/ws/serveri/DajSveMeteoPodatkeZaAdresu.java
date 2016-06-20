
package org.foi.nwtis.dkopic2.ws.serveri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dajSveMeteoPodatkeZaAdresu complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dajSveMeteoPodatkeZaAdresu"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="adresa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dajSveMeteoPodatkeZaAdresu", propOrder = {
    "adresa"
})
public class DajSveMeteoPodatkeZaAdresu {

    protected String adresa;

    /**
     * Gets the value of the adresa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdresa() {
        return adresa;
    }

    /**
     * Sets the value of the adresa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdresa(String value) {
        this.adresa = value;
    }

}
