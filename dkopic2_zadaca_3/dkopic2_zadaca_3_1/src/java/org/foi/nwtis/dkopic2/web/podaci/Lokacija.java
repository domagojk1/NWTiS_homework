/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.web.podaci;

/**
 * Klasa za spremanje jedne lokacije.
 * @author domagoj
 */
public class Lokacija {

    private String latitude;
    private String longitude;

    /**
     * Kreira novu instancu klase 'Lokacija'.
     */
    public Lokacija() {
    }

    /**
     * Kreira novu instancu klase 'Lokacija'.
     * @param latitude geografska širina
     * @param longitude geografska dužina
     */
    public Lokacija(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
}