/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.dkopic2.web.slusaci.SlusacAplikacije;

/**
 * Klasa za povezivanje na bazu podataka. Otvara konekciju, upisuje podatke te
 * provjerava postoje li određeni podaci u bazi.
 * @author domagoj
 */
public class Database {
    private String url = SlusacAplikacije.dbConfig.dajPostavku("server.database") + SlusacAplikacije.dbConfig.dajPostavku("user.database");
    private String user = SlusacAplikacije.dbConfig.dajPostavku("user.username");
    private String password = SlusacAplikacije.dbConfig.dajPostavku("user.password");
    private String client = SlusacAplikacije.dbConfig.dajPostavku("driver.database.derby");
    
    /**
     * Otvara konekciju sa bazom.
     * @return Connection
     */
    private Connection openConnection() {
        Connection connection = null;
        
        try 
        {
            connection = DriverManager.getConnection(url, user, password);
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return connection;
    }
    
    /**
     * Upisuje podatke u bazu podataka.
     * @param element naziv elementa
     * @param type vrsta elementa
     * @throws SQLException 
     */
    public void insertData(String element, String type) throws SQLException {
        String insertString = "INSERT INTO ELEMENTI (vrsta, naziv) VALUES (?,?)";
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        
        try 
        {
            connection = openConnection();
            
            if(connection != null)
            {
                preparedStatement = connection.prepareStatement(insertString);
                preparedStatement.setString(1, type);
                preparedStatement.setString(2, element.toUpperCase());
                preparedStatement.executeUpdate();

                System.out.println(element + " " + type + " upisan u bazu");
            }
 
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            if (preparedStatement != null)
                preparedStatement.close();
            
            if (connection != null)
                connection.close();
        }
    }
    
    /**
     * Vrši SELECT upit nad bazom.
     * @param name Naziv elementa
     * @param type Vrsta elementa
     * @return true ukoliko postoji podatak, inače false
     * @throws SQLException 
     */
    public boolean checkRecord(String name,String type) throws SQLException {
        String selectSql = "SELECT * FROM ELEMENTI WHERE naziv = ? AND vrsta = ?";
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        Connection connection = null;
        boolean contains = false;
        
        try 
        {
            connection = openConnection();
            
            if(connection != null)
            {
                preparedStatement = connection.prepareStatement(selectSql);
                preparedStatement.setString(1, name.toUpperCase());
                preparedStatement.setString(2, type);

                result = preparedStatement.executeQuery();
                while(result.next())
                {
                    String n = result.getString("naziv");
                    if (name.toUpperCase().equals(n))
                    {
                        contains = true;
                        break;
                    }
                }
            }
            
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            if (preparedStatement != null)
                preparedStatement.close();
            
            if (result != null)
                result.close();
            
            if (connection != null )
                connection.close();
        }
        
        return contains;
    }
}
