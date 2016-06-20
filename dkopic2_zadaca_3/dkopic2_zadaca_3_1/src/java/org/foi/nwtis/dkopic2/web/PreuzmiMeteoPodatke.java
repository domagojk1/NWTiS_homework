/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.web;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.dkopic2.Database;
import org.foi.nwtis.dkopic2.rest.klijenti.OWMKlijent;
import org.foi.nwtis.dkopic2.web.podaci.Adresa;
import org.foi.nwtis.dkopic2.web.podaci.MeteoPodaci;

/**
 * Klasa (dretva) koja služi za zapisivanje meteo podataka u bazu.
 * @author domagoj
 */
public class PreuzmiMeteoPodatke extends Thread {
    private Database database;
    private ArrayList<Adresa> addresses;
    private int interval;
    
    /**
     * Kreira novu instancu klase 'PreuzmiMeteoPodatke.
     * @param interval interval dretve
     */
    public PreuzmiMeteoPodatke(int interval) {
        this.interval = interval;
        database = new Database();
    }

    /**
     * Pokreće rad dretve. 
     * Poziva metodu downloadData().
     */
    @Override
    public void run() {
        while(true)
        {
            downloadData();
            
            try 
            {
                Thread.sleep(interval * 1000);
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(PreuzmiMeteoPodatke.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Pokreće metodu run.
     */
    @Override
    public synchronized void start() {
        super.start(); 
    }
    
    /**
     * Dohvaća podatke za adrese putem openweathermap.org API-a i zapisuje ih u bazu.
     */
    private void downloadData() {
        try 
        {
            addresses = new ArrayList<>(database.getAddresses());
            
            if(addresses != null)
            {
                for(Adresa address : addresses)
                {
                    OWMKlijent client = new OWMKlijent();
                    MeteoPodaci meteo = client.getRealTimeWeather(address.getGeoloc().getLatitude(), address.getGeoloc().getLongitude());
                    database.insertDataMeteo(meteo, address);
                }
                System.out.println("Upisani meteo podaci.");
            }
        } 
        catch (Exception ex)
        {
            Logger.getLogger(PreuzmiMeteoPodatke.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
