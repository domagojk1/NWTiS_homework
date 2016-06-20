/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.web.slusaci;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.foi.nwtis.dkopic2.konfiguracije.Konfiguracija;
import org.foi.nwtis.dkopic2.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.dkopic2.web.PreuzmiMeteoPodatke;

/**
 * Listener za aplikaciju.
 * @author domagoj
 */
public class SlusacAplikacije implements ServletContextListener {

    public static Konfiguracija dbConfig;
    public static Konfiguracija config;
    public static String path;
    private PreuzmiMeteoPodatke threadPreuzmi;
    public static volatile DataSource dataSource;
    
    /**
     * Iz konteksta dohvaća putanju te inicijalizacijske parametre. 
     * Kreira konfiguracijske objekte prema podacima iz konfiguracija.
     * Kreira org.apache.tomcat.jdbc.pool.DataSource za spajanje na bazu.
     * Pokreće dretvu za zapisivanje meteo podataka u bazu.
     * @param sce ServletContext
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
            ServletContext context = sce.getServletContext();
            String dbConfigFile = context.getInitParameter("konfiguracija");                        
            path = context.getRealPath("/WEB-INF") + File.separator;
            String configFile = context.getInitParameter("konfig_txt");
            
            try 
            {
                config = KonfiguracijaApstraktna.preuzmiKonfiguraciju(path + configFile);
                dbConfig = KonfiguracijaApstraktna.preuzmiKonfiguraciju(path + dbConfigFile);
                context.setAttribute("dbConfig",dbConfig);
                context.setAttribute("config", config);
                
                System.out.println("Učitana konfiguracija.");
            } 
            catch (Exception ex) 
            {
                Logger.getLogger(SlusacAplikacije.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            PoolProperties properties = new PoolProperties();
            properties.setUrl(dbConfig.dajPostavku("server.database") + dbConfig.dajPostavku("user.database"));
            properties.setDriverClassName(dbConfig.dajPostavku("driver.database.derby"));
            properties.setUsername(dbConfig.dajPostavku("user.username"));
            properties.setPassword(dbConfig.dajPostavku("user.password"));
            dataSource = new org.apache.tomcat.jdbc.pool.DataSource(properties);
            dataSource.setPoolProperties(properties);
             
            int interval = Integer.parseInt(config.dajPostavku("intervalDretve"));
            threadPreuzmi = new PreuzmiMeteoPodatke(interval);
            threadPreuzmi.start();
    }

    /**
     * Zaustavlja rad dretve i zatvara org.apache.tomcat.jdbc.pool.DataSource.
     * @param sce ServletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
       if(threadPreuzmi != null)
       {
           threadPreuzmi.interrupt();
       }
       
       if(dataSource != null)
       {
           dataSource.close();
       }
       
       System.out.println("Context destroyed");
    }
}
