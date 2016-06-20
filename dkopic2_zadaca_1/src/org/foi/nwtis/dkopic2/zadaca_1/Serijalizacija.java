/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.zadaca_1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/***
 * Klasa koja sadrži metode za serijalizaciju i deserijalizaciju evidencije.
 * @author domagoj
 */
public class Serijalizacija {
   
   /***
    * Serijalizira objekt evidencije
    * @param file - ime datoteke u koju se zapisuje
    * @param record - objekt evidencije
    * @throws FileNotFoundException
    * @throws IOException 
    */
   public static synchronized void serializeToFile(String file, Evidencija record) 
           throws FileNotFoundException, IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(record);
        objectOutputStream.close();
        fileOutputStream.close();
    }
   
   /***
    * Deserijalizira objekt evidencije.
    * @param file - ime datoteke
    * @return - objekt evidencije
    * @throws FileNotFoundException
    * @throws IOException 
    */
   public static synchronized Evidencija deserializeFromFile(String file) 
           throws FileNotFoundException, IOException {
        Evidencija gameRecord = null;
        
        FileInputStream inputStream = new FileInputStream(file);
        ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
        try 
        {
            gameRecord = (Evidencija) objInputStream.readObject();
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(SerijalizatorEvidencije.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        inputStream.close();
        objInputStream.close();
        return gameRecord;
    }
}
