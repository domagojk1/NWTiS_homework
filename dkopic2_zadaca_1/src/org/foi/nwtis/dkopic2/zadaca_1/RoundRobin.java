/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.zadaca_1;

import java.net.Socket;
import java.util.Iterator;
import java.util.List;

/***
 * Klasa koja implementira kružno posluživanje dretvi.
 * @author domagoj
 */
public class RoundRobin {
    
    private Iterator<ObradaZahtjeva> it;
    private List<ObradaZahtjeva> list;

    /***
     * @param list - lista dretvi ObradaZahtjeva 
     */
    public RoundRobin(List<ObradaZahtjeva> list) {
        this.list = list;
        it = list.iterator();
    }
    
    /***
     * Odabire sljedeću dretvu u kružnom posluživanju (ako je lista došla do kraja vraća na početak).
     * Odabranoj dretvi postavlja socket, a ukoliko još nije pokrenuta pokreće je.
     * Ispisuje se koja je dretva zauzeta, a koja odabrana.
     * @param socket 
     */
    public void next(Socket socket) {
        if(isLast())
            it = list.iterator();
        
        ObradaZahtjeva request = it.next();
        
        if(!request.isAlive())
        {
            request.setSocket(socket);
            request.start();
            System.out.println("Odabrana je dretva " + request.getThreadName());
        }
        else
        {
            if(request.getSocket() == null)
            {
                request.setSocket(socket);
                System.out.println("Odabrana je dretva " + request.getThreadName());
            }
            else
                System.out.println("Zauzeta dretva " + request.getThreadName());
        }
    }
    
    /***
     * Provjerava je li iterator liste došao do kraja.
     * @return - true ukoliko postoji slijedeći element liste, inače false
     */
    public boolean isLast() {
        if(!it.hasNext()) 
            return true;
       
        return false;
    }
}
