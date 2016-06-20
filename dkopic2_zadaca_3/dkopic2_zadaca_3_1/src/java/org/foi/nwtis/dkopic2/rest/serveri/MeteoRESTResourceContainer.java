/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.rest.serveri;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.foi.nwtis.dkopic2.Database;
import org.foi.nwtis.dkopic2.web.podaci.Adresa;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author domagoj
 */
@Path("/meteoREST")
public class MeteoRESTResourceContainer {

    @Context
    private UriInfo context;

    /**
     * Kreira instancu klase 'MeteoRESTResourceContainer'.
     */
    public MeteoRESTResourceContainer() {
    }

    /**
     * GET metoda koja dohvaća sve adrese iz tablice 'adrese'.
     * @return JSON string adresa
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        Database database = new Database();
        ArrayList<Adresa> addressList = null;
        JSONArray array = null;
       
        try 
        {
            addressList = new ArrayList<>(database.getAddresses());
            array = new JSONArray();
            
            for(Adresa address : addressList)
            {
               JSONObject object = new JSONObject();
               object.put("id", address.getIdadresa());
               object.put("adresa", address.getAdresa());
               JSONArray jsonArray = new JSONArray();
               JSONObject obj = new JSONObject();
               obj.put("lattitude", address.getGeoloc().getLatitude());
               obj.put("longitude", address.getGeoloc().getLongitude());
               jsonArray.put(obj);
               object.put("geolokacija", (Object)jsonArray);
               array.put(object);
            }
        } 
        catch (Exception ex) {
            Logger.getLogger(MeteoRESTResourceContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return array.toString();
    }

    /**
     * POST metoda za ažuriranje ili kreiranje novog objekta.
     * @param content
     * @return 
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(String content) {
        return Response.created(context.getAbsolutePath()).build();
    }

    /**
     * Locira podresurs za {id}.
     * @param id
     * @return 
     */
    @Path("{id}")
    public MeteoRESTResource getMeteoRESTResource(@PathParam("id") String id) {
        return MeteoRESTResource.getInstance(id);
    }
}
