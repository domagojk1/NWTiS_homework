/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.web.zrna;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.foi.nwtis.dkopic2.rest.klijenti.ForecastREST;
import org.foi.nwtis.dkopic2.ws.serveri.MeteoPodaci;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Klasa za petodnevnu vremensku prognozu.
 * @author domagoj
 */
@Named(value = "forecast")
@RequestScoped
public class Forecast {
    private ArrayList<MeteoPodaci> meteoList;
    private String address;
            
    /**
     * Kreira novu instancu klase Forecast
     */
    public Forecast() {
    }

    public ArrayList<MeteoPodaci> getMeteoList() {
        return meteoList;
    }

    public void setMeteoList(ArrayList<MeteoPodaci> meteoList) {
        this.meteoList = meteoList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * Poziva REST API putem klijenta i dohvaća vremensku prognozu.
     * @param addresses List
     * @return "FORECAST" ako je sve u redu, inače "ERROR"
     */
    public String getForecast(List<String> addresses) {
        String message = "ERROR";
        setAddress(addresses.get(0));
        meteoList = new ArrayList<>();
        ForecastREST forecast = new ForecastREST(addresses.get(0));
        String response = forecast.getJson();
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        
        try 
        {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray array = (JSONArray)jsonObject.get("list");
            
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject object = array.getJSONObject(i).getJSONObject("main");
                
                MeteoPodaci meteo = new MeteoPodaci();
                
                meteo.setTemperatureValue(object.getDouble("temp"));
                meteo.setTemperatureMin(object.getDouble("temp_min"));
                meteo.setTemperatureMax(object.getDouble("temp_max"));
                meteo.setPressureValue(object.getDouble("pressure"));
                meteo.setHumidityValue(object.getDouble("humidity"));
                meteo.setWeatherValue(array.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description"));
                
                object = array.getJSONObject(i).getJSONObject("wind");
                meteo.setWindSpeedValue(object.getDouble("speed"));
                meteo.setWindDirectionValue(object.getDouble("deg"));
                
                try 
                {
                    Date date = format.parse(array.getJSONObject(i).getString("dt_txt"));
                    meteo.setLastUpdate(date);
                } 
                catch (Exception ex) 
                {
                    Logger.getLogger(Forecast.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                meteoList.add(meteo);
            }
            message = "FORECAST";
        } 
        catch (JSONException ex) 
        {
            Logger.getLogger(Forecast.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return message;
    }
}
