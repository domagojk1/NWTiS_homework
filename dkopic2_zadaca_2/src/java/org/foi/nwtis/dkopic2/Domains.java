/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2;

import java.util.ArrayList;

/**
 * Sadrži domene koje treba pretražiti za naziv elementa.
 * @author domagoj
 */
public class Domains {
    public static ArrayList<String> domains = new ArrayList(){
        {
            add(".hr");
            add(".info");
            add(".com");
            add(".eu");
        }
    };
}
