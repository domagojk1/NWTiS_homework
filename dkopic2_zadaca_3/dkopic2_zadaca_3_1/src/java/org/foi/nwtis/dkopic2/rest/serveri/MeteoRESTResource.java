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

/**
 * REST Web Service
 *
 * @author domagoj
 */
public class MeteoRESTResource {
    private String id;

    /**
     * Kreira instancu klase 'MeteoRESTResource'.
     * @param id id
     */
    private MeteoRESTResource(String id) {
        this.id = id;
    }

    /**
     * Vraća instancu klase 'MeteoRESTResource'.
     * @param id id
     * @return objekt klase MeteoRESTResource
     */
    public static MeteoRESTResource getInstance(String id) {
        return new MeteoRESTResource(id);
    }

    /**
     * GET metoda koja vraća meteo podatke prema id-u adrese.
     * @return meteo podaci u JSON stringu
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        Database database = new Database();
        String jsonResponse = null;
        
        try 
        {
            jsonResponse = database.getMeteo(Integer.parseInt(id));
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(MeteoRESTResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return jsonResponse;
    }
    
    /**
     * PUT metoda
     * @param content 
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    /**
     * DELETE metoda
     */
    @DELETE
    public void delete() {
    }
}
