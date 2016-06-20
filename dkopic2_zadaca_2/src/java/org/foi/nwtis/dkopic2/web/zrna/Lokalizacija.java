/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.web.zrna;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.faces.context.FacesContext;

/**
 * Klasa za odabir jezika.
 * @author domagoj
 */
@Named(value = "lokalizacija")
@SessionScoped
public class Lokalizacija implements Serializable {

    private Map<String, Object> jezici;
    private String odabraniJezik;
    private Locale odabraniLocale;
   
    /**
     * Postavlja hrvatski jezik za početni.
     */
    public Lokalizacija() {
        jezici = new HashMap<String, Object>();
        jezici.put("Hrvatski", new Locale("hr"));
        jezici.put("English", Locale.ENGLISH);
        jezici.put("Deutsch", Locale.GERMAN);
        odabraniLocale = new Locale("hr");
    }
    
    /**
     * Akcija za odabir jezika. 
     * @return
     * @throws IOException 
     */
    public Object odaberiJezik() throws IOException {
        for (Map.Entry<String, Object> entry : jezici.entrySet()) 
        {
            if (entry.getValue().toString().equals(odabraniJezik)) 
            {
                setOdabraniLocale((Locale) entry.getValue());
                FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) entry.getValue());
                return "OK";
            }
        }
        return "ERROR";
    }
    
    public Map<String, Object> getJezici() {
        return jezici;
    }

    public void setJezici(Map<String, Object> jezici) {
        this.jezici = jezici;
    }

    public String getOdabraniJezik() {
        return odabraniJezik;
    }

    public void setOdabraniJezik(String odabraniJezik) {
        this.odabraniJezik = odabraniJezik;
    }

    public Locale getOdabraniLocale() {
        return odabraniLocale;
    }

    public void setOdabraniLocale(Locale odabraniLocale) {
        this.odabraniLocale = odabraniLocale;
    }
    
}
    
