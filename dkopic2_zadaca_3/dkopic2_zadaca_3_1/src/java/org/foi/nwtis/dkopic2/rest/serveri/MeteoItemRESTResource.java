/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.rest.serveri;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.dkopic2.Database;
import org.foi.nwtis.dkopic2.web.podaci.MeteoPodaci;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author domagoj
 */
public class MeteoItemRESTResource {

    private String address;

    /**
     * Kreira novu instancu klase.
     * @param address naziv adrese
     */
    private MeteoItemRESTResource(String address) {
        this.address = address;
    }

    /**
     * Vraća instancu 'MeteoItemRESTResource' klase.
     * @param address naziv adrese
     * @return instanca 'ForecastRESTResource'
     */
    public static MeteoItemRESTResource getInstance(String address) {
        return new MeteoItemRESTResource(address);
    }

    /**
     * GET metoda koja dohvaća zadnje meteo podatke neke adrese.
     * @return meteo podaci u JSON stringu
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        String jsonResponse = "";
        JSONArray array = null;
        
        Database database = new Database();
        try 
        {
            MeteoPodaci meteoData = database.getLastMeteo(address);
            array = new JSONArray();
            JSONObject object = new JSONObject();
            
            object.put("adresa", address);
            object.put("vrijeme", meteoData.getWeatherValue());
            object.put("temperatura", meteoData.getTemperatureValue());
            object.put("temperatura_min", meteoData.getTemperatureMin());
            object.put("temperatura_max", meteoData.getTemperatureMax());
            object.put("tlak", meteoData.getHumidityValue());
            object.put("brzina_vjetra", meteoData.getWindSpeedValue());
            object.put("smjer_vjetra", meteoData.getWindDirectionValue());
            object.put("preuzeto", meteoData.getLastUpdate());
            
            array.put(object);
            jsonResponse = array.toString();
        } 
        catch (Exception ex)
        {
            Logger.getLogger(MeteoItemRESTResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return jsonResponse;
    }

    /**
     * PUT metoda.
     * @param content 
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    /***
     * DELETE metoda.
     */
    @DELETE
    public void delete() {
    }
}
