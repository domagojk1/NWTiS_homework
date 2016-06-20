/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2;

import java.util.Date;

/**
 * Predstavlja zapis jedne datoteke sadržaja preuzetg sa web stranice.
 * @author domagoj
 */
public class Datoteka {
    private String absolutePath;
    private String name;
    private String size;
    private Date date;

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Date getDateTime() {
        return date;
    }

    public void setDateTime(Date date) {
        this.date = date;
    }
}
