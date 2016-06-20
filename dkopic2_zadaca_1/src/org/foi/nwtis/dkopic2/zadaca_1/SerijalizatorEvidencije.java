/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.zadaca_1;

import java.io.IOException;
import org.foi.nwtis.dkopic2.konfiguracije.Konfiguracija;
import org.foi.nwtis.dkopic2.zadaca_1.Serijalizacija;
import org.foi.nwtis.dkopic2.zadaca_1.ServerSustava;

/***
 * Klasa koja u vremenskim intervalima serijalizira evidenciju.
 * @author domagoj
 */
public class SerijalizatorEvidencije extends Thread{
    private Konfiguracija config;
    private ServerSustava server;
    private String fileName;
    private int serInterval;

    /***
     * @param server - server sustava
     * @param config - konfiguracija
     */
    public SerijalizatorEvidencije(ServerSustava server, Konfiguracija config) {
        this.server = server;
        this.config = config;
    }

    @Override
    public void interrupt() {
        super.interrupt(); 
    }

    /***
     * Serijalizira evidenciju u određenim intervalima iz konfiguracije.
     */
    @Override
    public void run() {
        while(true)
        {           
            serInterval = Integer.parseInt(config.dajPostavku("intervalSerijalizacije"));
            fileName = config.dajPostavku("evidDatoteka");
            
            try 
            {
                Serijalizacija.serializeToFile(fileName, server.gameRecord);
                
                if(ServerSustava.isStopped()) 
                {
                    Serijalizacija.serializeToFile(fileName, server.gameRecord);
                    System.exit(0);
                }
                
                sleep(serInterval);
            } 
            catch (IOException ex) 
            {
                System.out.println("ERROR 03; Nešto je pošlo po krivu sa serijalizacijom");
            } 
            catch (InterruptedException ex) {
                System.out.println("ERROR 03; Nešto je pošlo po krivu sa serijalizacijom");
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start();
        System.out.println("Dretva za serijalizaciju je pocela sa radom.");
    }
}
