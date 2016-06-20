/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.ws.serveri;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.foi.nwtis.dkopic2.Database;
import org.foi.nwtis.dkopic2.rest.klijenti.GMKlijent;
import org.foi.nwtis.dkopic2.rest.klijenti.OWMKlijent;
import org.foi.nwtis.dkopic2.web.podaci.Adresa;
import org.foi.nwtis.dkopic2.web.podaci.Lokacija;
import org.foi.nwtis.dkopic2.web.podaci.MeteoPodaci;

/**
 * Web servis.
 * @author domagoj
 */
@WebService(serviceName = "GeoMeteoWS")
public class GeoMeteoWS {
    private Database database;
    private static int brojac = 0;

    /**
     * Operacija web servisa za dohvaćanje svih adresa iz baze.
     * @return java.util.List
     */
    @WebMethod(operationName = "dajSveAdrese")
    public java.util.List<Adresa> dajSveAdrese() {
        List<Adresa> addressList = null;
        database = new Database();
        
        try 
        {
            addressList = new ArrayList<>(database.getAddresses());
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return addressList;
    }
    
    /**
     * Kreira objekt klase 'Adresa' prema imenu adrese.
     * @param address naziv adrese
     * @return objekt klase Adresa
     */
    Adresa getAddress(String address) {
        GMKlijent gmk = new GMKlijent();
        Lokacija lokacija = gmk.getGeoLocation(address);
        Adresa adresa = new Adresa();
        adresa.setAdresa(address);
        adresa.setGeoloc(lokacija);
        adresa.setIdadresa(brojac++);
        
        return adresa;
    }

    /**
     * Operacija web servisa za dohvaćanje vremenske prognoze prema nazivu adrese.
     * @param adresa naziv adrese
     * @return MeteoPodaci
     */
    @WebMethod(operationName = "dajVazeceMeteoPodatkeZaAdresu")
    public MeteoPodaci dajVazeceMeteoPodatkeZaAdresu(@WebParam(name = "adresa") String adresa) {
        Adresa address = getAddress(adresa);
        OWMKlijent owmk = new OWMKlijent();
        
        MeteoPodaci meteoData = owmk.getRealTimeWeather(address.getGeoloc().getLatitude(), address.getGeoloc().getLongitude());
        return meteoData;
    }
    
    /**
     * Operacija web servisa za dohvaćanje zadnjih upisanih meteo podataka u bazi.
     * @param adresa naziva adrese
     * @return MeteoPodaci
     */
    @WebMethod(operationName = "dajZadnjeMeteoPodatkeZaAdresu")
    public MeteoPodaci dajZadnjeMeteoPodatkeZaAdresu(@WebParam(name = "adresa") String adresa) {
        MeteoPodaci meteo = null;
        Database database = new Database();
        
        try 
        {
            meteo = database.getLastMeteo(adresa);
        } 
        catch (SQLException ex) {
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return meteo;
    }

    /**
     * Operacija web servisa za dohvaćanje svih meteo podataka adrese.
     * @param adresa naziv adrese
     * @return java.util.List
     */
    @WebMethod(operationName = "dajSveMeteoPodatkeZaAdresu")
    public java.util.List<MeteoPodaci> dajSveMeteoPodatkeZaAdresu(@WebParam(name = "adresa") String adresa) {
        ArrayList<MeteoPodaci> meteoList = null;
        Database database = new Database();
        
        try 
        {
            meteoList = new ArrayList<>(database.getAllMeteo(adresa));
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return meteoList;
    }
}