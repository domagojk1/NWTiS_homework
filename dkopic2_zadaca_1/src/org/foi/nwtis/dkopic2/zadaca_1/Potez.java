/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.zadaca_1;

import java.io.Serializable;

/***
 * Klasa koja predstavlja jedan potez igrača.
 * @author domagoj
 */
public class Potez implements Serializable{
    public String user;
    public int x;
    public int y;
    public boolean shipKilled;

    /***
     * @param user - korisničko ime
     * @param x - koordinata x
     * @param y - koordinata y
     * @param shipKilled - true ako je brod potopljen, inače false
     */
    public Potez(String user, int x, int y, boolean shipKilled) {
        this.user = user;
        this.x = x;
        this.y = y;
        this.shipKilled = shipKilled;
    } 
}
