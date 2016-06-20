/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.rest.serveri;

import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.dkopic2.rest.klijenti.OWMKlijent;

/**
 * REST Web Service
 *
 * @author domagoj
 */
public class ForecastRESTResource {

    private String address;

    /**
     * Kreira novu instancu klase.
     * @param address naziv adrese
     */
    private ForecastRESTResource(String address) {
        this.address = address;
    }

    /**
     * Vraća instancu 'ForecastRESTResource' klase.
     * @param address naziv adrese
     * @return instanca 'ForecastRESTResource'
     */
    public static ForecastRESTResource getInstance(String address) {
        return new ForecastRESTResource(address);
    }

    /**
     * GET metoda koja vraća meteo prognozu za idućih 5 dana.
     * @return meteo podaci u JSON stringu
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        OWMKlijent client = new OWMKlijent();
        return client.getForecast(address);
    }

    /**
     * PUT metoda za ažuriranje ili kreiranje instance 'ForecastRESTResource'.
     * @param content 
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    /**
     * Delete metoda.
     */
    @DELETE
    public void delete() {
    }
}
