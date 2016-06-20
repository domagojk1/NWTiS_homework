/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2;

import com.sun.mail.imap.IMAPInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import org.foi.nwtis.dkopic2.konfiguracije.Konfiguracija;

/**
 * Klasa za obradu mail poruka.
 * @author domagoj
 */
public class MailProcess extends Thread{

    private String host;
    private int port;
    private int interval;
    private String username;
    private String password;
    private String subjectReceived;
    private String ispravnePoruke;
    private String ostalePoruke;
    private String address;
    private String subjectToSend;
    private Session session;
    private Store store;
    private Folder folder;
    private Konfiguracija config;
    private Database database;
    private Matcher matcher;
    private static final String messageRegex = "^(GRAD|TVRTKA) ([a-zA-ZšđčćžŠĐČĆŽ ]*); (ADD|UPDATE);$";
    private String path;
    private int N;
    private long startTime;
    private long endTime;
    private int numOfAddedCities;
    private int numOfAddedCompanies;
    private int numOfUpdatedCities;
    private int numOfUpdatedCompanies;
    
    /**
     * Dohvaća konfiguracijske podatke.
     * @param config - Konfiguracija
     * @param path - WEB-INF putanja
     */
    public MailProcess(Konfiguracija config, String path) {
        this.config = config;
        this.path = path;
        
        fetchConfigs();
        database = new Database();
    }
    
    /**
     * Dohvaća konfiguracijske podatke.
     */
    private void fetchConfigs() {
        host = config.dajPostavku("posluzitelj");
        port = Integer.parseInt(config.dajPostavku("port"));
        interval = Integer.parseInt(config.dajPostavku("interval"));
        username = config.dajPostavku("username");
        password = config.dajPostavku("password");
        subjectReceived = config.dajPostavku("trazeniPredmet");
        ispravnePoruke = config.dajPostavku("ispravnePoruke");
        ostalePoruke = config.dajPostavku("ostalePoruke");
        address = config.dajPostavku("adresaPoruke");
        subjectToSend = config.dajPostavku("predmetPoruke");
    }
    
    /**
     * Pokreće se obrada mail poruka.
     */
    @Override
    public void run() {        
       while(true)
       {
            checkMail();
            try 
            {
                Thread.sleep(interval * 1000);
            } 
            catch (InterruptedException ex)
            {
                Logger.getLogger(MailProcess.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
    }

    /**
     * Pokreće run metodu dretve.
     */
    @Override
    public synchronized void start() {
        super.start();
    }
    
    /**
     * Provjerava postoji li poruka u mailu korisnika prema konfiguraciji i vrši određene akcije ukoliko postoji.
     */
    private void checkMail() {
        numOfAddedCities = 0;
        numOfAddedCompanies =  0;
        numOfUpdatedCities = 0; 
        numOfUpdatedCompanies = 0;
        
        try 
        {
            session = Session.getDefaultInstance(System.getProperties(), null);
            store = session.getStore("imap");
            store.connect(host, port, username, password);
            folder = store.getDefaultFolder();
            folder = folder.getFolder("inbox");
            folder.open(Folder.READ_WRITE);
            
            Flags seen = new Flags(Flags.Flag.SEEN);
            FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
            Message[] messages = folder.search(unseenFlagTerm);
            
            startTime = System.currentTimeMillis();
            
            for (int i = 0; i < messages.length; i++)
            {
                MimeMessage message = (MimeMessage) messages[i];
                N  = message.getMessageNumber();
                Object object = (Object) message.getContent();
                
                if(message.getSubject().equals(subjectReceived))
                {
                   if(object instanceof String)
                    {
                        String body = (String) object;
                        String msg = body.replaceAll("(\\r|\\n)", "");
                        proccessMail(msg, message);
                    }
                    else if(object instanceof IMAPInputStream)
                    {
                        IMAPInputStream input = (IMAPInputStream) object;
                        StringBuilder sb = new StringBuilder();
                        String line;
                        
                        BufferedReader br = new BufferedReader(new InputStreamReader(input));
                        
                        while ((line = br.readLine()) != null)
                        {
				sb.append(line);
			}
                        
                        if(br != null)
                            br.close();
                        
                        proccessMail(sb.toString(), message);
                    } 
                }
                
                message.setFlag(Flags.Flag.SEEN, true);
            }
            
            endTime = System.currentTimeMillis();
            String formatted = String.format(Locale.US, "%,d", N).replace(',', '.');
            String subject = subjectToSend + formatted;
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh.mm.ss.zzz");
            String start = formatter.format(startTime);
            String end = formatter.format(endTime);
            long duration = endTime - startTime;

            String content = "Obrada započela u: " + start + "\n" + 
                             "Obrada završila u: " + end + "\n" + 
                             "Trajanje obrade u ms: " + duration + "\n" +
                             "Broj poruka: " + N + "\n" + 
                             "Broj dodanih podataka GRAD: " + numOfAddedCities + "\n" +
                             "Broj dodanih podataka TVRTKA: " + numOfAddedCompanies + "\n" + 
                             "Broj azuriranih podataka GRAD: " + numOfUpdatedCities + "\n" + 
                             "Broj azuriranih podataka TVRTKA: " + numOfUpdatedCompanies;
            
            sendMessage(content, subject);
        
            if(store != null)
                store.close();
        } 
        catch (java.lang.IllegalStateException ex)
        {
           
        }
        catch (SQLException ex) 
        {
           ex.printStackTrace();
        }
        catch(MessagingException ex)
        {
            ex.printStackTrace();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
   
    /**
     * Vrši akcije ukoliko mail sadrži ispravnu sintaksu. Ako je ispravan, sprema ga u folder Nwtis_IspravnePoruke,
     * te upisuje podatke u bazu, u suprotnom sprema u folder Nwtis_OstalePoruke.
     * @param msg - Tekst poruke
     * @param message - Poruka
     * @throws IOException
     * @throws SQLException 
     */
    private void proccessMail(String msg,Message message) throws IOException, SQLException {
        if(validateMessage(msg))
        {
            storeMessage(ispravnePoruke, message);

            String name = matcher.group(2);
            URLSearch urlSearch = new URLSearch(name,path);
            urlSearch.downloadContent();

            if(msg.contains("GRAD"))
            {
               String city = matcher.group(2);
               String command = matcher.group(3);

               if(command.equals("ADD"))
               {
                   if(!database.checkRecord(city, "GRAD"))
                   {
                       database.insertData(city, "GRAD");
                       numOfAddedCities ++;
                   }
                   else System.out.println("Pogreska, vec postoji u bazi.");
               }
               else
               {
                   if(database.checkRecord(city, "GRAD"))
                   {
                       database.insertData(city, "GRAD");
                       numOfUpdatedCities ++;
                   }
                   else System.out.println("Pogreška, ne postoji u bazi.");
               }
            }
            else 
            {
                String firm = matcher.group(2);
                String command = matcher.group(3);

                if(command.equals("ADD"))
                {
                   if(!database.checkRecord(firm, "TVRTKA"))
                   {
                       database.insertData(firm, "TVRTKA");
                       numOfAddedCompanies ++;
                   }
                   else System.out.println("Pogreska, vec postoji u bazi.");
               }
               else 
               {
                   if(database.checkRecord(firm, "TVRTKA"))
                   {
                       database.insertData(firm, "TVRTKA");
                       numOfUpdatedCompanies ++;
                   }
                   else System.out.println("Pogreška, ne postoji u bazi.");
               }
            }
        }
        else
        {
            storeMessage(ostalePoruke, message);
        }            
    }
    
    /**
     * Validira mail poruku u skladu sa regularnim izrazom.
     * @param message - Tekst poruke
     * @return - true ukoliko prolazi regex, inače false
     */
    private boolean validateMessage(String message) {
        Pattern pattern = Pattern.compile(messageRegex);
        matcher = pattern.matcher(message);
        
        return matcher.matches();
    }
    
    /**
     * Pohranjuje poruku u određeni folder.
     * @param pathToStore - Naziv folder
     * @param messageToStore - Poruka
     * @throws IOException 
     */
    private void storeMessage(String pathToStore, Message messageToStore) throws IOException {
        try 
        {
            Folder folder = store.getFolder(pathToStore);
            
            if(! folder.exists())
                folder.create(Folder.HOLDS_MESSAGES);
            
            folder.appendMessages(new Message[]{messageToStore});
            
            if(folder.isOpen()) 
                folder.close(true);
            
            System.out.println("Poruka prebacena u folder: " + pathToStore);
        } 
        catch (MessagingException ex)
        {
            Logger.getLogger(MailProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    /**
     * Šalje mail poruku korisniku prema konfiguraciji.
     * @param content - Tekst poruke
     * @param subject - Predmet poruke
     */
    private void sendMessage(String content, String subject) {
        try 
        {
            MimeMessage message = new MimeMessage(session);
        
            Address fromAddress = new InternetAddress(username);
            message.setHeader("Content-Type", "text/html");
            message.setFrom(fromAddress);

            Address[] toAddresses = InternetAddress.parse(address);

            message.setRecipients(Message.RecipientType.TO, toAddresses);
            message.setSubject(subject);
            message.setText(content);
            
            Transport.send(message);
        } 
        catch (MessagingException ex)
        {
            Logger.getLogger(MailProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
