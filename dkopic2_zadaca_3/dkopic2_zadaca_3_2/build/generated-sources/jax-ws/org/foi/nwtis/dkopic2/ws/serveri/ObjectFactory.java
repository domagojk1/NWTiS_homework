
package org.foi.nwtis.dkopic2.ws.serveri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.foi.nwtis.dkopic2.ws.serveri package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DajSveAdrese_QNAME = new QName("http://serveri.ws.dkopic2.nwtis.foi.org/", "dajSveAdrese");
    private final static QName _DajSveAdreseResponse_QNAME = new QName("http://serveri.ws.dkopic2.nwtis.foi.org/", "dajSveAdreseResponse");
    private final static QName _DajSveMeteoPodatkeZaAdresu_QNAME = new QName("http://serveri.ws.dkopic2.nwtis.foi.org/", "dajSveMeteoPodatkeZaAdresu");
    private final static QName _DajSveMeteoPodatkeZaAdresuResponse_QNAME = new QName("http://serveri.ws.dkopic2.nwtis.foi.org/", "dajSveMeteoPodatkeZaAdresuResponse");
    private final static QName _DajVazeceMeteoPodatkeZaAdresu_QNAME = new QName("http://serveri.ws.dkopic2.nwtis.foi.org/", "dajVazeceMeteoPodatkeZaAdresu");
    private final static QName _DajVazeceMeteoPodatkeZaAdresuResponse_QNAME = new QName("http://serveri.ws.dkopic2.nwtis.foi.org/", "dajVazeceMeteoPodatkeZaAdresuResponse");
    private final static QName _DajZadnjeMeteoPodatkeZaAdresu_QNAME = new QName("http://serveri.ws.dkopic2.nwtis.foi.org/", "dajZadnjeMeteoPodatkeZaAdresu");
    private final static QName _DajZadnjeMeteoPodatkeZaAdresuResponse_QNAME = new QName("http://serveri.ws.dkopic2.nwtis.foi.org/", "dajZadnjeMeteoPodatkeZaAdresuResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.foi.nwtis.dkopic2.ws.serveri
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DajSveAdrese }
     * 
     */
    public DajSveAdrese createDajSveAdrese() {
        return new DajSveAdrese();
    }

    /**
     * Create an instance of {@link DajSveAdreseResponse }
     * 
     */
    public DajSveAdreseResponse createDajSveAdreseResponse() {
        return new DajSveAdreseResponse();
    }

    /**
     * Create an instance of {@link DajSveMeteoPodatkeZaAdresu }
     * 
     */
    public DajSveMeteoPodatkeZaAdresu createDajSveMeteoPodatkeZaAdresu() {
        return new DajSveMeteoPodatkeZaAdresu();
    }

    /**
     * Create an instance of {@link DajSveMeteoPodatkeZaAdresuResponse }
     * 
     */
    public DajSveMeteoPodatkeZaAdresuResponse createDajSveMeteoPodatkeZaAdresuResponse() {
        return new DajSveMeteoPodatkeZaAdresuResponse();
    }

    /**
     * Create an instance of {@link DajVazeceMeteoPodatkeZaAdresu }
     * 
     */
    public DajVazeceMeteoPodatkeZaAdresu createDajVazeceMeteoPodatkeZaAdresu() {
        return new DajVazeceMeteoPodatkeZaAdresu();
    }

    /**
     * Create an instance of {@link DajVazeceMeteoPodatkeZaAdresuResponse }
     * 
     */
    public DajVazeceMeteoPodatkeZaAdresuResponse createDajVazeceMeteoPodatkeZaAdresuResponse() {
        return new DajVazeceMeteoPodatkeZaAdresuResponse();
    }

    /**
     * Create an instance of {@link DajZadnjeMeteoPodatkeZaAdresu }
     * 
     */
    public DajZadnjeMeteoPodatkeZaAdresu createDajZadnjeMeteoPodatkeZaAdresu() {
        return new DajZadnjeMeteoPodatkeZaAdresu();
    }

    /**
     * Create an instance of {@link DajZadnjeMeteoPodatkeZaAdresuResponse }
     * 
     */
    public DajZadnjeMeteoPodatkeZaAdresuResponse createDajZadnjeMeteoPodatkeZaAdresuResponse() {
        return new DajZadnjeMeteoPodatkeZaAdresuResponse();
    }

    /**
     * Create an instance of {@link MeteoPodaci }
     * 
     */
    public MeteoPodaci createMeteoPodaci() {
        return new MeteoPodaci();
    }

    /**
     * Create an instance of {@link Adresa }
     * 
     */
    public Adresa createAdresa() {
        return new Adresa();
    }

    /**
     * Create an instance of {@link Lokacija }
     * 
     */
    public Lokacija createLokacija() {
        return new Lokacija();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DajSveAdrese }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://serveri.ws.dkopic2.nwtis.foi.org/", name = "dajSveAdrese")
    public JAXBElement<DajSveAdrese> createDajSveAdrese(DajSveAdrese value) {
        return new JAXBElement<DajSveAdrese>(_DajSveAdrese_QNAME, DajSveAdrese.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DajSveAdreseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://serveri.ws.dkopic2.nwtis.foi.org/", name = "dajSveAdreseResponse")
    public JAXBElement<DajSveAdreseResponse> createDajSveAdreseResponse(DajSveAdreseResponse value) {
        return new JAXBElement<DajSveAdreseResponse>(_DajSveAdreseResponse_QNAME, DajSveAdreseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DajSveMeteoPodatkeZaAdresu }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://serveri.ws.dkopic2.nwtis.foi.org/", name = "dajSveMeteoPodatkeZaAdresu")
    public JAXBElement<DajSveMeteoPodatkeZaAdresu> createDajSveMeteoPodatkeZaAdresu(DajSveMeteoPodatkeZaAdresu value) {
        return new JAXBElement<DajSveMeteoPodatkeZaAdresu>(_DajSveMeteoPodatkeZaAdresu_QNAME, DajSveMeteoPodatkeZaAdresu.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DajSveMeteoPodatkeZaAdresuResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://serveri.ws.dkopic2.nwtis.foi.org/", name = "dajSveMeteoPodatkeZaAdresuResponse")
    public JAXBElement<DajSveMeteoPodatkeZaAdresuResponse> createDajSveMeteoPodatkeZaAdresuResponse(DajSveMeteoPodatkeZaAdresuResponse value) {
        return new JAXBElement<DajSveMeteoPodatkeZaAdresuResponse>(_DajSveMeteoPodatkeZaAdresuResponse_QNAME, DajSveMeteoPodatkeZaAdresuResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DajVazeceMeteoPodatkeZaAdresu }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://serveri.ws.dkopic2.nwtis.foi.org/", name = "dajVazeceMeteoPodatkeZaAdresu")
    public JAXBElement<DajVazeceMeteoPodatkeZaAdresu> createDajVazeceMeteoPodatkeZaAdresu(DajVazeceMeteoPodatkeZaAdresu value) {
        return new JAXBElement<DajVazeceMeteoPodatkeZaAdresu>(_DajVazeceMeteoPodatkeZaAdresu_QNAME, DajVazeceMeteoPodatkeZaAdresu.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DajVazeceMeteoPodatkeZaAdresuResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://serveri.ws.dkopic2.nwtis.foi.org/", name = "dajVazeceMeteoPodatkeZaAdresuResponse")
    public JAXBElement<DajVazeceMeteoPodatkeZaAdresuResponse> createDajVazeceMeteoPodatkeZaAdresuResponse(DajVazeceMeteoPodatkeZaAdresuResponse value) {
        return new JAXBElement<DajVazeceMeteoPodatkeZaAdresuResponse>(_DajVazeceMeteoPodatkeZaAdresuResponse_QNAME, DajVazeceMeteoPodatkeZaAdresuResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DajZadnjeMeteoPodatkeZaAdresu }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://serveri.ws.dkopic2.nwtis.foi.org/", name = "dajZadnjeMeteoPodatkeZaAdresu")
    public JAXBElement<DajZadnjeMeteoPodatkeZaAdresu> createDajZadnjeMeteoPodatkeZaAdresu(DajZadnjeMeteoPodatkeZaAdresu value) {
        return new JAXBElement<DajZadnjeMeteoPodatkeZaAdresu>(_DajZadnjeMeteoPodatkeZaAdresu_QNAME, DajZadnjeMeteoPodatkeZaAdresu.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DajZadnjeMeteoPodatkeZaAdresuResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://serveri.ws.dkopic2.nwtis.foi.org/", name = "dajZadnjeMeteoPodatkeZaAdresuResponse")
    public JAXBElement<DajZadnjeMeteoPodatkeZaAdresuResponse> createDajZadnjeMeteoPodatkeZaAdresuResponse(DajZadnjeMeteoPodatkeZaAdresuResponse value) {
        return new JAXBElement<DajZadnjeMeteoPodatkeZaAdresuResponse>(_DajZadnjeMeteoPodatkeZaAdresuResponse_QNAME, DajZadnjeMeteoPodatkeZaAdresuResponse.class, null, value);
    }

}
