/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.rest.serveri;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 * Klasa za učitavanje REST resursa.
 * @author domagoj
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    /**
     * Vraća učitane resurse (HashSet).
     * @return resursi
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Učitava klasu.
     * @param resources 
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(org.foi.nwtis.dkopic2.rest.serveri.ForecastRESTResource.class);
        resources.add(org.foi.nwtis.dkopic2.rest.serveri.ForecastRESTResourceContainer.class);
        resources.add(org.foi.nwtis.dkopic2.rest.serveri.MeteoItemRESTResource.class);
        resources.add(org.foi.nwtis.dkopic2.rest.serveri.MeteoItemRESTResourceContainer.class);
        resources.add(org.foi.nwtis.dkopic2.rest.serveri.MeteoRESTResource.class);
        resources.add(org.foi.nwtis.dkopic2.rest.serveri.MeteoRESTResourceContainer.class);
    }
    
}
