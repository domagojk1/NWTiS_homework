/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.web.zrna;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.foi.nwtis.dkopic2.Datoteka;
import org.foi.nwtis.dkopic2.web.slusaci.SlusacAplikacije;

/**
 * Klasa za pregled direktorija sa spremljenim podacima o web stranicama.
 * @author domagoj
 */
@Named(value = "pregledPreuzetihPodataka")
@RequestScoped
public class PregledPreuzetihPodataka {

    private ArrayList<Datoteka> files;
    /**
     * Poziva metodu za dohvat podataka o direktorijima.
     */
    public PregledPreuzetihPodataka() {
        files = new ArrayList<Datoteka>();
        listFiles(SlusacAplikacije.path + "podaci" + File.separator, files);
    }
    
    public ArrayList<Datoteka> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<Datoteka> files) {
        this.files = files;
    }
    
    /**
     * Dohvaća direktorije unutar proslijeđenog direktorija.
     * @param directoryName naziv direktorija
     * @param files lista objekata klase Datoteka
     */
    private void listFiles(String directoryName, ArrayList<Datoteka> files) {
        File directory = new File(directoryName);
        File[] list = directory.listFiles();
        
        for (File file : list)
        {
            if (file.isFile())
            {
                Datoteka datoteka = new Datoteka();
                datoteka.setAbsolutePath(file.getAbsolutePath());
                datoteka.setName(file.getName());
                long sizeInKb = file.length() / 1024;
                datoteka.setSize(String.valueOf(sizeInKb) + " KB");
                datoteka.setDateTime(new Date(file.lastModified()));
                files.add(datoteka);
            }
            else if (file.isDirectory())
            {
                listFiles(file.getAbsolutePath(), files);
            }
        }
    }
}
