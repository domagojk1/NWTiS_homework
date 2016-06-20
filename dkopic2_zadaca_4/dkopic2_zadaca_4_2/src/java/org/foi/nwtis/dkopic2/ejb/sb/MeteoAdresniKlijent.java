/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.ejb.sb;

import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import org.foi.nwtis.dkopic2.rest.klijenti.GMKlijent;
import org.foi.nwtis.dkopic2.rest.klijenti.OWMKlijent;
import org.foi.nwtis.dkopic2.web.podaci.Lokacija;
import org.foi.nwtis.dkopic2.web.podaci.MeteoPodaci;
import org.foi.nwtis.dkopic2.web.podaci.MeteoPrognoza;

/**
 *
 * @author domagoj
 */
@Stateless
@LocalBean
public class MeteoAdresniKlijent {
    private String apiKey;

    /**
     * Postavlja APPID za web servis
     * @param apiKey ključ
     */
    public void postaviKorisnickePodatke(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Vraća meteo podatke za traženu adresu
     * @param address adresa
     * @return MeteoPodaci
     */
    public MeteoPodaci dajVazeceMeteoPodatke(String address) {
        MeteoPodaci meteoData = null;
        
        GMKlijent gmClient = new GMKlijent();
        Lokacija lokacija = gmClient.getGeoLocation(address);
        OWMKlijent owmClient = new OWMKlijent(apiKey);
        meteoData = owmClient.getRealTimeWeather(lokacija.getLatitude(), lokacija.getLongitude());
        
        return meteoData;
    }

    /**
     * Vraća geolokaciju prema adresi
     * @param address adresa
     * @return Lokacija
     */
    public Lokacija getLocation(String address) {
        GMKlijent gmClient = new GMKlijent();
        return gmClient.getGeoLocation(address);
    }

    /**
     * Dohvaća prognozu za 5 dana prema adresi
     * @param address adresa
     * @return lista MeteoPrognoza
     */
    public ArrayList<MeteoPrognoza> getMeteoForecast(String address) {
        OWMKlijent owmClient = new OWMKlijent(apiKey);
        return owmClient.getForecast(address);
    }
    
    
}
