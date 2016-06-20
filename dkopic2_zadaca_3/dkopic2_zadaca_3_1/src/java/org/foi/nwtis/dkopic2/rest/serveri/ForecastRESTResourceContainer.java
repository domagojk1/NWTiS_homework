/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.rest.serveri;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author domagoj
 */
@Path("/forecastREST")
public class ForecastRESTResourceContainer {

    @Context
    private UriInfo context;

    /**
     * Kreira novu instancu klase 'ForecastRESTResourceContainer'.
     */
    public ForecastRESTResourceContainer() {
    }

    /**
     * Get metoda.
     * @return 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * POST metoda.
     * @param content
     * @return 
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(String content) {
        //TODO
        return Response.created(context.getAbsolutePath()).build();
    }

    /**
     * Locira podresurs za {address}.
     * @param address
     * @return 
     */
    @Path("{address}")
    public ForecastRESTResource getForecastRESTResource(@PathParam("address") String address) {
        return ForecastRESTResource.getInstance(address);
    }
}
