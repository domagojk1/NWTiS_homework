/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.web.slusaci;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.foi.nwtis.dkopic2.konfiguracije.Konfiguracija;
import org.foi.nwtis.dkopic2.konfiguracije.KonfiguracijaApstraktna;


/**
 * Listener za aplikaciju.
 * @author domagoj
 */

public class SlusacAplikacije implements ServletContextListener {
    public static Konfiguracija config;  
    
    /**
     * Iz konteksta dohvaća putanju te inicijalizacijske parametre. 
     * Kreira konfiguracijski objekti prema podacima iz konfiguracija.
     * @param sce ServletContext
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
            ServletContext context = sce.getServletContext();                       
            String path = context.getRealPath("/WEB-INF") + File.separator;
            String configFile = context.getInitParameter("konfig_txt");
            
            try 
            {
                config = KonfiguracijaApstraktna.preuzmiKonfiguraciju(path + configFile);
            } 
            catch (Exception ex) 
            {
                Logger.getLogger(SlusacAplikacije.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
   
    }
}
