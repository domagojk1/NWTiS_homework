/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.rest.klijenti;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.dkopic2.web.podaci.Lokacija;
import org.foi.nwtis.dkopic2.web.podaci.MeteoPodaci;
import org.foi.nwtis.dkopic2.web.podaci.MeteoPrognoza;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Klasa za pozivanje openweathermap.org API-a.
 * @author domagoj
 */
public class OWMKlijent {
    private String apiKey;
    private OWMRESTHelper helper;
    private Client client;

    /**
     * Konstruktor, dohvaća API key i inicijalizira objekte klasi Client i OWMRESTHelper.
     */
    public OWMKlijent(String apiKey) {
        this.apiKey = apiKey;
        helper = new OWMRESTHelper(apiKey);
        client = ClientBuilder.newClient();
    }

    /**
     * Dohvaća vremensku prognozu pozivajući API prema geografskoj širini i dužini.
     * @param latitude geografska širina
     * @param longitude geografska dužina
     * @return objekt klase MeteoPodaci
     */
    public MeteoPodaci getRealTimeWeather(String latitude, String longitude) {
        WebTarget webResource = client.target(OWMRESTHelper.getOWM_BASE_URI())
                .path(OWMRESTHelper.getOWM_Current_Path());
        webResource = webResource.queryParam("lat", latitude);
        webResource = webResource.queryParam("lon", longitude);
        webResource = webResource.queryParam("lang", "hr");
        webResource = webResource.queryParam("units", "metric");
        webResource = webResource.queryParam("APIKEY", apiKey);
        
        String odgovor = webResource.request(MediaType.APPLICATION_JSON).get(String.class);
        try 
        {
            JsonReader reader = Json.createReader(new StringReader(odgovor));

            JsonObject jo = reader.readObject();

            MeteoPodaci mp = new MeteoPodaci();
            mp.setSunRise(new Date(jo.getJsonObject("sys").getJsonNumber("sunrise").bigDecimalValue().longValue()*1000));
            mp.setSunSet(new Date(jo.getJsonObject("sys").getJsonNumber("sunset").bigDecimalValue().longValue()*1000));
            
            mp.setTemperatureValue(new Double(jo.getJsonObject("main").getJsonNumber("temp").doubleValue()));
            mp.setTemperatureMin(new Double(jo.getJsonObject("main").getJsonNumber("temp_min").doubleValue()));
            mp.setTemperatureMax(new Double(jo.getJsonObject("main").getJsonNumber("temp_max").doubleValue()));
            mp.setTemperatureUnit("celsius");
            
            mp.setHumidityValue(new Double(jo.getJsonObject("main").getJsonNumber("humidity").doubleValue()));
            mp.setHumidityUnit("%");
            
            mp.setPressureValue(new Double(jo.getJsonObject("main").getJsonNumber("pressure").doubleValue()));
            mp.setPressureUnit("hPa");
            
            mp.setWindSpeedValue(new Double(jo.getJsonObject("wind").getJsonNumber("speed").doubleValue()));
            mp.setWindSpeedName("");
            
            mp.setWindDirectionValue(new Double(jo.getJsonObject("wind").getJsonNumber("deg").doubleValue()));
            mp.setWindDirectionCode("");
            mp.setWindDirectionName("");
            
            mp.setCloudsValue(jo.getJsonObject("clouds").getInt("all"));
            mp.setCloudsName(jo.getJsonArray("weather").getJsonObject(0).getString("description"));
            mp.setPrecipitationMode("");
            
            mp.setWeatherNumber(jo.getJsonArray("weather").getJsonObject(0).getInt("id"));
            mp.setWeatherValue(jo.getJsonArray("weather").getJsonObject(0).getString("description"));
            mp.setWeatherIcon(jo.getJsonArray("weather").getJsonObject(0).getString("icon"));
            
            mp.setLastUpdate(new Date(jo.getJsonNumber("dt").bigDecimalValue().longValue()*1000));
            return mp;
            
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    
    /**
     * Dohvaća vremensku prognozu u idućih 5 dana prema proslijeđenoj adresi pozivajući API.
     * @param address naziv adrese
     * @return JSON odgovor
     */
    public ArrayList<MeteoPrognoza> getForecast(String address) {
        Date date = null;
        ArrayList<MeteoPrognoza> list = null;
        GMKlijent gmClient = new GMKlijent();
        Lokacija location = gmClient.getGeoLocation(address);
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        
        WebTarget webResource = client.target(OWMRESTHelper.getOWM_BASE_URI())
                .path(OWMRESTHelper.getOWM_Forecast_Path());
        webResource = webResource.queryParam("lat", location.getLatitude());
        webResource = webResource.queryParam("lon", location.getLongitude());
        webResource = webResource.queryParam("lang", "hr");
        webResource = webResource.queryParam("units", "metric");
        webResource = webResource.queryParam("APIKEY", apiKey);
        
        String response = webResource.request(MediaType.APPLICATION_JSON).get(String.class);
        
        try 
        {
            list = new ArrayList<>();
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
                    long d = array.getJSONObject(i).getLong("dt");
                    date = new Date(d * 1000);
                    meteo.setLastUpdate(date);
                } 
                catch (Exception ex) 
                {
                    Logger.getLogger(OWMKlijent.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                
                MeteoPrognoza forecast = new MeteoPrognoza();
                forecast.setAdresa(address);
                forecast.setPrognoza(meteo);
                forecast.setDan(calendar.get(Calendar.DAY_OF_MONTH));
                
                list.add(forecast);
            }
        } 
        catch (JSONException ex) {
            Logger.getLogger(OWMKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
}
