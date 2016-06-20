/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.web.zrna;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.foi.nwtis.dkopic2.rest.klijenti.MeteoREST;
import org.foi.nwtis.dkopic2.ws.serveri.MeteoPodaci;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Klasa za trenutnu vremensku prognozu.
 * @author domagoj
 */
@Named(value = "meteo")
@RequestScoped
public class Meteo {
    private ArrayList<MeteoPodaci> meteoList;
    
    /**
     * Kreira novu instancu klase Meteo.
     */
    public Meteo() {
    }

    public ArrayList<MeteoPodaci> getMeteoList() {
        return meteoList;
    }

    public void setMeteoList(ArrayList<MeteoPodaci> meteoList) {
        this.meteoList = meteoList;
    }

    /**
     * Poziva REST API putem klijenta za dohvat vremenske prognoze.
     * @param addresses List
     * @return "METEO" ako je sve u redu, inače "ERROR"
     */
    public String getMeteoData(List<String> addresses) {
        meteoList = new ArrayList<>();
        String message = "METEO";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
       
        for(String address : addresses)
        {
            MeteoREST meteo = new MeteoREST(address);
            String response = meteo.getJson();
            
            try 
            {
                JSONArray array = new JSONArray(response);
                JSONObject object = array.getJSONObject(0);
                
                MeteoPodaci meteoData = new MeteoPodaci();
                meteoData.setWeatherValue(object.getString("vrijeme"));
                meteoData.setPressureValue(object.getDouble("tlak"));
                meteoData.setWindSpeedValue(object.getDouble("brzina_vjetra"));
                meteoData.setLastUpdate(formatter.parse(object.getString("preuzeto")));
                meteoData.setWindDirectionValue(object.getDouble("smjer_vjetra"));
                meteoData.setTemperatureMin(object.getDouble("temperatura_min"));
                meteoData.setTemperatureMax(object.getDouble("temperatura_max"));
                meteoData.setTemperatureValue(object.getDouble("temperatura"));
                meteoData.setWeatherIcon(address);
                meteoList.add(meteoData);
            } 
            catch (Exception ex)
            {
                message = "ERROR";
                Logger.getLogger(Meteo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return message;
    }
    
}
