/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.zadaca_1;

import java.io.Serializable;

/***
 * Predstavlja jedan brod unutar igre.
 * @author domagoj
 */
public class Brod implements Serializable{
    public int x;
    public int y;
    public String user;
    public boolean isKilled;

    /***
     * @param x - koordinata x
     * @param y - koordinata y
     * @param user - korisnicko ime
     * @param isKilled - true ukoliko je brod potopljen, inače false
     */
    public Brod(int x, int y, String user, boolean isKilled) {
        this.x = x;
        this.y = y;
        this.user = user;
        this.isKilled = isKilled;
    }
}
