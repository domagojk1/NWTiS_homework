/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.web.zrna;

import java.io.File;
import java.util.Scanner;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 * Klasa za pregled podataka datoteke.
 * @author domagoj
 */
@Named(value = "pregledDatoteke")
@RequestScoped
public class PregledDatoteke {

    private String content;
    private String name;
    /**
     * Kreira novu instancu PregledDatoteke.
     */
    public PregledDatoteke() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    /**
     * Preuzima podatke iz datoteke.
     * @param absolutePath apsolutna putanja do datoteke
     * @param name naziv datoteke
     * @return sadržaj datoteke
     */
    public String preuzmiPodatke(String absolutePath, String name) {
        File file = null; 
        Scanner scanner = null;
        setName(name);
        
        try 
        {
            file = new File(absolutePath);
            
            StringBuilder fileContents = new StringBuilder((int)file.length());
            scanner = new Scanner(file);
            String lineSeparator = System.getProperty("line.separator");
            
            while(scanner.hasNextLine()) 
            {        
                fileContents.append(scanner.nextLine() + lineSeparator);
            }
            setContent(fileContents.toString());
            
            if(scanner != null)
                scanner.close();
           
            return "OK";
        }
        catch (Exception ex)    
        {
            ex.printStackTrace();
            return "ERROR";
        }
    }
    
    /**
     * Služi kao akcija za povratak na pregled svih podataka.
     * @return OK
     */
    public String povratakPregledPreuzetihPodataka() {
        return "OK";
    }
}
