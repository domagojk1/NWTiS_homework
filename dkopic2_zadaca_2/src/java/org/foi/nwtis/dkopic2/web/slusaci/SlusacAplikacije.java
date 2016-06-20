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
import org.foi.nwtis.dkopic2.MailProcess;
import org.foi.nwtis.dkopic2.konfiguracije.Konfiguracija;
import org.foi.nwtis.dkopic2.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.dkopic2.konfiguracije.NemaKonfiguracije;

/**
 * Listener za aplikaciju.
 * @author domagoj
 */
public class SlusacAplikacije implements ServletContextListener {

    public static Konfiguracija dbConfig;
    public static Konfiguracija config;
    public static String path;
    private MailProcess mailProcess;
    
    
    /**
     * Iz konteksta dohvaća putanju te inicijalizacijske parametre. 
     * Kreira konfiguracijske objekte prema podacima iz konfiguracija, pokreće dretvu za rad s mail porukama.
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
            catch (NemaKonfiguracije ex) 
            {
                Logger.getLogger(SlusacAplikacije.class.getName()).log(Level.SEVERE, null, ex);
            }
              
            mailProcess = new MailProcess(config,path);
            mailProcess.start();
    }

    /**
     * Zastavlja dretvu za obradu mail poruka.
     * @param sce 
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (mailProcess != null)
        {
            mailProcess.interrupt();
        }
    }
}