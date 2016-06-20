/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.ws.klijenti;

/**
 * Klijent GeoMeteoWS-a.
 * @author domagoj
 */
public class MeteoWSKlijent {

    /**
     * Dohvaća sve adrese iz baze.
     * @return java.util.List
     */
    public static java.util.List<org.foi.nwtis.dkopic2.ws.serveri.Adresa> dajSveAdrese() {
        org.foi.nwtis.dkopic2.ws.serveri.GeoMeteoWS_Service service = new org.foi.nwtis.dkopic2.ws.serveri.GeoMeteoWS_Service();
        org.foi.nwtis.dkopic2.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajSveAdrese();
    }

    /**
     * Dohvaća sve podatke o adresama iz baze.
     * @param adresa naziv adrese
     * @return java.util.List
     */
    public static java.util.List<org.foi.nwtis.dkopic2.ws.serveri.MeteoPodaci> dajSveMeteoPodatkeZaAdresu(java.lang.String adresa) {
        org.foi.nwtis.dkopic2.ws.serveri.GeoMeteoWS_Service service = new org.foi.nwtis.dkopic2.ws.serveri.GeoMeteoWS_Service();
        org.foi.nwtis.dkopic2.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajSveMeteoPodatkeZaAdresu(adresa);
    }
    
}
