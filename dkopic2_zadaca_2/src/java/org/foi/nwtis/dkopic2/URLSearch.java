/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Klasa za pretraživanje web stranica prema nazivu i domenama.
 * @author domagoj
 */
public class URLSearch {
    private String name;
    private ArrayList<String> urls;
    private String path;
    private String folderName;

    /**
     * Poziva metodu kreiranje URL-ova.
     * @param name naziv grada/tvrtke
     * @param path WEB-INF putanja
     */
    public URLSearch(String name, String path) {
        this.name = name;
        this.path = path + "podaci" + File.separator;
        
        createUrls();
    }
    
    /**
     * Kreira URL-ove prema nazivu i domenama.
     */
    private void createUrls() {
        urls = new ArrayList<>();
        
        for(String domain : Domains.domains)
        {
            urls.add("http://www." + this.name + domain);
        }
    }
    
    /**
     * Preuzima sadržaj sa URL-a ukoliko je on valjan i zapisuje ga u datoteku.
     */
    public void downloadContent() {
        URL page = null;
        URLConnection connection = null;
        
        for(String url : urls)
        {
            try 
            {
                page = new URL(url);
                connection = page.openConnection();
                connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
                
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                createFolder();
                String fileName = page.getHost() + ".txt";
                String newFilePath = path + getFolderName() + File.separator + fileName;
                BufferedWriter out = new BufferedWriter(new FileWriter(newFilePath));
                
                String inputLine;
                while((inputLine = in.readLine()) != null)
                {
                    out.write(inputLine);
                    out.write("\n");
                }
                in.close();
                out.close();
                System.out.println("Preuzet sadrzaj sa stranice: " + page.getHost());
            } 
            catch (Exception ex) 
            {
                System.out.println(page.getHost() + " ne postoji.");
            }
        }
    }
    
    /**
     * Kreira folder za spremanje podataka o web stranici.
     */
    private void createFolder() {
        setFolderName();
        
        File dir = new File(path + getFolderName());
        dir.mkdirs();
    }
    
    /**
     * Postavlja naziv kreiranog foldera.
     */
    private void setFolderName() {
        folderName = this.name + "_";
        
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        
        String fileExt = df.format(date);
        folderName = folderName + fileExt;
    }
    
    private String getFolderName() {
        return this.folderName;
    }
}
