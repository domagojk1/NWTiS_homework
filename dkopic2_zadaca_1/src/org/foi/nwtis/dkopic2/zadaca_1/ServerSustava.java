/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.zadaca_1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.nwtis.dkopic2.konfiguracije.Konfiguracija;
import org.foi.nwtis.dkopic2.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.dkopic2.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.dkopic2.konfiguracije.NemaKonfiguracije;

/***
 * Klasa koja implementira server.
 * @author domagoj
 */
public class ServerSustava {
    String regexServer = "^-server -konf ([a-zA-Z0-9_.]*)( -load)?";
    private String fileName;
    private boolean load;
    public static Konfiguracija config;
    private int port;
    private int threadNum;
    private List<ObradaZahtjeva> threadList;
    private RoundRobin robin;
    public Evidencija gameRecord;
    private ThreadGroup threadGroup;
    private static boolean paused;
    private static boolean stopped;
    private SerijalizatorEvidencije recordSerialization;
    private ServerSocket serverSocket;
    private Pattern pattern;
    private Matcher matcher;

    /***
     * Konstruktor klase server.
     * Provjerava se ispravnost komandi i učitava datoteka konfiguracije.
     * @param command - komanda prema kojoj server određuje svoj posao
     */
    public ServerSustava(String command) {
         
        if(validateParams(command)) 
        {
            String fileName = matcher.group(1);
            
            if(matcher.group(2) != null)
                    load = true;
            try 
            {
                config = KonfiguracijaApstraktna.preuzmiKonfiguraciju(fileName);
                System.out.println("Ucitana konfiguracija iz " + fileName);
                gameRecord = new Evidencija();
                
                start();
            } 
            catch (NemaKonfiguracije ex) 
            {
                System.out.println("Ne postoji datoteka konfiguracije.");
                System.exit(0);
            }
        }
        else System.out.println("ERROR 90; Neispravni argumenti!");
    }
    
    /***
     * Pokreće rad servera.
     * Inicijalizira se igra, pokreće serijalizacija evidencije te kreira grupu dretvi.
     * Postavlja server socket koji čeka na zahtjeve klijenata te pokreće odgovarajuću dretvu.
     */
    public void start() {
        gameInitialize();
        
        recordSerialization = new SerijalizatorEvidencije(this, config);
        recordSerialization.start();
        
        port = Integer.parseInt(config.dajPostavku("port"));
        
        try 
        {
            serverSocket = new ServerSocket(port,threadNum);
            createThreads();
            
            while(!isStopped())
            {
               Socket clientSocket = serverSocket.accept();
               runThread(clientSocket); 
            }
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ServerSustava.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    /***
     * Inicijalizira igru.
     * Ukoliko je upisan parametar 'load' učitavaju se podaci iz zadnje kreirane datoteke evidencije.
     * Ako nije upisan parametar 'load' kreira se nova igra te nova datoteka evidencije za odgovarajuću igru,
     * također se u datoteki konfiguracije postavlja kao datoteka evidencije zadnja kreirana datoteka.
     */
    private void gameInitialize() {
        if(load)
        {
            try 
            {
                String file = config.dajPostavku("evidDatoteka");
                gameRecord = Serijalizacija.deserializeFromFile(file);
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(ServerSustava.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Ucitavanje evidencije nije uspjelo (datoteka nije pronadena).");
            }
        }
        else
        {
            String file = config.dajPostavku("evidDatoteka");
            String fileNumber = file.replaceAll("\\D+","");
            int numberNext = Integer.parseInt(fileNumber) + 1;
            String newFile = "NWTiS_evidencija" + String.format("%04d", numberNext) + ".bin";
            
            config.obrisiPostavku("evidDatoteka");
            config.spremiPostavku("evidDatoteka", newFile);
            try 
            {
                config.spremiKonfiguraciju();
            } 
            catch (NeispravnaKonfiguracija ex) 
            {
                Logger.getLogger(ServerSustava.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            gameRecord.createBoard();
            gameRecord.generateShips();
        }
    }
    
    /***
     * Šalje zahtjev idućoj dretvi unutar kružnog posluživanja.
     * @param socket - klijentski socket
     */
    private void runThread(Socket socket) {
        robin.next(socket);
    }
    
    /***
     * Kreira grupu dretvi te joj pridružuje broj dretvi prema postavkama u konfiguraciji.
     */
    private void createThreads() {
       threadGroup = new ThreadGroup("dkopic");
       threadList = new ArrayList<ObradaZahtjeva>();
       threadNum = Integer.parseInt(config.dajPostavku("brojDretvi"));
       
       for(int i = 1; i <= threadNum; i++)
       {
           ObradaZahtjeva requestThread = new ObradaZahtjeva(threadGroup, i, gameRecord);
           threadList.add(requestThread);
       }
        
       robin = new RoundRobin(threadList);
    }
    
    /***
     * Provjerava parametre programa prema specifikacijama serverskih komandi (regexu).
     * @param command - komanda za rad servera
     * @return - true ukoliko parametri odgovaraju regexu, inače false
     */
    private boolean validateParams(String command) {
        pattern = Pattern.compile(regexServer);
        matcher = pattern.matcher(command);
        
        return matcher.matches();
    }
    
    /***
     * Provjerava je li server u stanju 'pause'.
     * @return - true ukoliko je u stanju 'pause', inače false
     */
    public static boolean isPaused() {
        return paused;
    }
    
    /***
     * Postavlja status servera.
     * @param paused - true za pauziranje, inače false
     */
    public synchronized static void setPaused(boolean paused) {
        ServerSustava.paused = paused;
    }
    
    /***
     * Provjerava je li server u stanju 'stop'.
     * @return - true ukoliko je u stanju 'stop', inače false
     */
    public static boolean isStopped() {
        return stopped;
    }
    
    /***
     * Postavlja status servera.
     * @param stopped - true za stopiranje, inače false
     */
    public synchronized static void setStopped(boolean stopped) {
        ServerSustava.stopped = stopped;
    }
}
