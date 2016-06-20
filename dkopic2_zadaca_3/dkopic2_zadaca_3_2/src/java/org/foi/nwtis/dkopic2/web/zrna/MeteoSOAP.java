/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.web.zrna;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.foi.nwtis.dkopic2.ws.klijenti.MeteoWSKlijent;
import org.foi.nwtis.dkopic2.ws.serveri.MeteoPodaci;

/**
 * Klasa za dohvat meteo podataka iz baze.
 * @author domagoj
 */
@Named(value = "meteoData")
@RequestScoped
public class MeteoSOAP {
    private List<MeteoPodaci> meteoList; 
    
    /**
     * Kreira novu instancu klase MeteoData.
     */
    public MeteoSOAP() {
        
    }

    public List<MeteoPodaci> getMeteoList() {
        return meteoList;
    }

    public void setMeteoList(List<MeteoPodaci> meteoList) {
        this.meteoList = meteoList;
    }
    
    /**
     * Poziva SOAP servis koji dohvaća podatke iz baze.
     * @param addresses List
     * @return "OK-SOAP"
     */
    public String callSoapService(List<String> addresses) {
        meteoList = new ArrayList<>(MeteoWSKlijent.dajSveMeteoPodatkeZaAdresu(addresses.get(0)));
        return "OK-SOAP";
    }
}
