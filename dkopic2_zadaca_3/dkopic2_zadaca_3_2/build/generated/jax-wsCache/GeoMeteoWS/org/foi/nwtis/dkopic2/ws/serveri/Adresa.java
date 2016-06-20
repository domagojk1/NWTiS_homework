
package org.foi.nwtis.dkopic2.ws.serveri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for adresa complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="adresa"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="adresa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="geoloc" type="{http://serveri.ws.dkopic2.nwtis.foi.org/}lokacija" minOccurs="0"/&gt;
 *         &lt;element name="idadresa" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "adresa", propOrder = {
    "adresa",
    "geoloc",
    "idadresa"
})
public class Adresa {

    protected String adresa;
    protected Lokacija geoloc;
    protected int idadresa;

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

    /**
     * Gets the value of the geoloc property.
     * 
     * @return
     *     possible object is
     *     {@link Lokacija }
     *     
     */
    public Lokacija getGeoloc() {
        return geoloc;
    }

    /**
     * Sets the value of the geoloc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Lokacija }
     *     
     */
    public void setGeoloc(Lokacija value) {
        this.geoloc = value;
    }

    /**
     * Gets the value of the idadresa property.
     * 
     */
    public int getIdadresa() {
        return idadresa;
    }

    /**
     * Sets the value of the idadresa property.
     * 
     */
    public void setIdadresa(int value) {
        this.idadresa = value;
    }

}
