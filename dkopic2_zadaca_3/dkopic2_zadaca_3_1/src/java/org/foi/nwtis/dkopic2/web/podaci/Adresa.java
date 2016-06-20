/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.web.podaci;

/**
 * Klasa koja predstavlja jednu adresu.
 * @author domagoj
 */
public class Adresa {
    private int idadresa;
    private String adresa;
    private Lokacija geoloc;

    /**
     * Kreira novu instancu klase 'Adresa'.
     */
    public Adresa() {
    }

    /**
     * Kreira novu instancu klase 'Adresa'.
     * @param idadresa id adrese
     * @param adresa naziva adrese
     * @param geoloc Lokacija
     */
    public Adresa(int idadresa, String adresa, Lokacija geoloc) {
        this.idadresa = idadresa;
        this.adresa = adresa;
        this.geoloc = geoloc;
    }

    public Lokacija getGeoloc() {
        return geoloc;
    }

    public void setGeoloc(Lokacija geoloc) {
        this.geoloc = geoloc;
    }

    public int getIdadresa() {
        return idadresa;
    }

    public void setIdadresa(int idadresa) {
        this.idadresa = idadresa;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
    
}
