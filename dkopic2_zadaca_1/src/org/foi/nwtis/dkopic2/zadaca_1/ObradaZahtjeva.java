/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.zadaca_1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * Klasa za obradu pojedinog zahtjeva.
 * @author domagoj
 */
public class ObradaZahtjeva extends Thread{
    private ServerSustava server;
    private volatile Socket socket; 
    private ThreadGroup group;
    private int id;
    private String adminRegex = "^USER ([A-Za-z0-9_,-]*); PASSWD ([A-Za-z0-9_,#,!,-]*); (PAUSE|START|NEW|STAT|STOP);";
    private String userRegex = "^USER ([A-Za-z0-9_,-]*); (PLAY|((10|[1-9]),(10|[1-9]))|STAT|);";
    private String showRegex = "";
    private InputStream input;
    private OutputStream output;
    private Evidencija gameRecord;
    
    /***
     * @param group - grupa dretvi
     * @param id - id dretve
     * @param gameRecord - evidencija
     */
    public ObradaZahtjeva(ThreadGroup group, int id, Evidencija gameRecord) {
        this.group = group;
        this.id = id;
        this.gameRecord = gameRecord;
    }
    
    /***
     * @return - ime dretve
     */
    public String getThreadName() {
        return this.group.getName() + " " + this.id;
    }

    /***
     * Postavlja klijentski socket
     * @param socket - socket
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /***
     * @return - socket
     */
    public Socket getSocket() {
        return socket;
    }
    
    /***
     * Pokreće se dretva ObradaZahtjeva.
     * U beskonačnoj petlji se provjerava je li pristigao novi zahtjev, što odgovara postavljenom socketu.
     * Ukoliko je pristigao zahtjev, preuzima podatke putem socketa i poziva prigodnu metodu za obradu tog zahtjeva.
     * Nakon što obradi neki od zahtjeva koji se tiču same igre, serijalizira evidenciju.
     */
    @Override
    public void run() {
        while(true)
        {
            if(getSocket() != null)
            {   
                try
                {
                    input = socket.getInputStream();
                    output = socket.getOutputStream();
                    StringBuilder command = new StringBuilder();
                    int data;
                    Pattern pattern;
                    Matcher matcher;

                    while(true)
                    {
                        data = input.read();
                        if(data == -1)
                            break;
                        command.append((char) data);
                    }
                    String commandString = command.toString().trim();

                    if(commandString.contains("PASSWD")) 
                    {
                        pattern = Pattern.compile(adminRegex);
                        matcher = pattern.matcher(commandString);

                        if(matcher.matches())
                        {
                            String username = matcher.group(1);
                            String password = matcher.group(2);
                            String commandAdmin = matcher.group(3);

                            if(commandAdmin.contains("PAUSE"))
                                adminPause(username,password);
                            else if(commandAdmin.contains("START"))
                                adminStart(username,password);
                            else if(commandAdmin.contains("STOP"))
                                adminStop(username,password);
                            else if(commandAdmin.contains("STAT"))
                                adminStat(username,password);
                            else if(commandAdmin.contains("NEW"))
                                adminNew(username,password);
                        }
                    }
                    else
                    {
                        pattern = Pattern.compile(userRegex);
                        matcher = pattern.matcher(commandString);

                        if(matcher.matches())
                        {
                            if(ServerSustava.isPaused())
                                getBackResponse("ERROR 10; Server je pauziran.");
                            else
                            {
                                String user = matcher.group(1);
                                if(matcher.group(3) == null)
                                {
                                    String userCommand = matcher.group(2);

                                    if(userCommand.contains("PLAY"))
                                        userPlay(user);
                                    else if(userCommand.contains("STAT"))
                                        userStat(user);
                                }
                                else
                                {
                                    int x = Integer.parseInt(matcher.group(4));
                                    int y = Integer.parseInt(matcher.group(5));
                                    userAttack(user, x, y);
                                }
                                
                                Serijalizacija.serializeToFile(ServerSustava.config.dajPostavku("evidDatoteka"), gameRecord);
                            }
                        }
                    }
                   
                    setSocket(null);
                } 
                catch (IOException ex) 
                {
                    Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            try 
            {
                Thread.sleep(500);
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start(); 
    }
    
    /***
     * Vraća odgovor klijentu.
     * @param response - odgovor
     */
    private void getBackResponse(String response){
        try 
        {
            output.write(response.getBytes());
            output.flush();
            input.close();
            output.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ObradaZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    /***
     * Provjerava je li korisničko ime administratora sustava odgovara onom u postavkama.
     * @param username - korisničko ime
     * @return - true ukoliko odgovara, inače false
     */
    private boolean isAdminUsername(String username) {
        String admin = ServerSustava.config.dajPostavku("adminKorIme");
        return admin.equals(username);
    }
    
    /***
     * Provjerava je li lozinka administratora sustava odgovara onoj u postavkama.
     * @param password - lozinka
     * @return - true ukoliko odgovara, inače false
     */
    private boolean isAdminPassword(String password) {
        String pass = ServerSustava.config.dajPostavku("adminKorLozinka");
        return pass.equals(password);
    }
    
    /***
     * Obrađuje administratorsku komandu 'PAUSE'. Ako je server već u stanju 'pause' ili ne odgovaraju korisničko ime
     * ili lozinka, vraća poruku greške klijentu. U suprotnom pauzira server i vraća odgovor "OK".
     * @param username - korisničko ime
     * @param password - lozinka
     */
    private void adminPause(String username, String password) {
        String response = null;
       
        if((!ServerSustava.isPaused()) && isAdminUsername(username) && isAdminPassword(password))
        {
            ServerSustava.setPaused(true);
            System.out.println("Server je pauziran.");
            response = "OK";
        }
        else if(ServerSustava.isPaused()) 
            response = "ERROR 01; Server je vec pauziran.";
        else if(!isAdminUsername(username))
            response = "ERROR 00; Korisnik nije administrator.";
        else if(!isAdminPassword(password))
            response = "ERORR 00; Netocan password.";
        
        getBackResponse(response);
    }
    
    /***
     * Obrađuje administratorsku komandu 'START'. Ukoliko server nije u stanju 'pause' ili ne odgovaraju korisničko ime
     * ili lozinka, vraća poruku greške klijentu. U suprotnom pokreće server i vraća odgovor "OK".
     * @param username - korisničko ime
     * @param password - lozinka
     */
    private void adminStart(String username, String password) {
        String response = null;
        
        if(ServerSustava.isPaused() && isAdminUsername(username) && isAdminPassword(password))
        {
            ServerSustava.setPaused(false);
            System.out.println("Server je pokrenut.");
            response = "OK";
        }
        else if(!ServerSustava.isPaused())
        {
            response = "ERROR 02: Server nije pauziran.";
        }
        else if(!isAdminUsername(username))
            response = "ERROR 00; Korisnik nije administrator.";
        else if(!isAdminPassword(password))
            response = "ERORR 00; Netocan password.";
        
        getBackResponse(response);
    }
    
    /***
     * Obrađuje administratorsku komandu 'STOP'. Ukoliko korisničko ime ili lozinka ne odgovaraju, vraća poruku greške. U suprotnom
     * prekida rad servera i vraća odgovor klijentu "OK".
     * @param username - korisničko ime
     * @param password - lozinka
     */
    private void adminStop(String username, String password) {
        String response = null;
        
        if(isAdminUsername(username) && isAdminPassword(password))
        {
            ServerSustava.setStopped(true);
            response = "OK";
        }
        else if(!isAdminUsername(username))
            response = "ERROR 00; Korisnik nije administrator.";
        else if(!isAdminPassword(password))
            response = "ERORR 00; Netocan password.";
        
        getBackResponse(response);
    }
    
    /***
     * Obrađuje administratorsku komandu 'STAT'. Ukoliko lozinka ili username ne odgovaraju vraća se poruka greške, u suprotnom se ispisuje
     * trenutno stanje ploče.
     * @param username - korisničko ime
     * @param password - lozinka
     */
    private void adminStat(String username, String password) {
        String response = "";
        
        if(isAdminUsername(username) && isAdminPassword(password))
        {
            response += "OK\nLEGENDA: vlasnik broda <- ubojica\n";
            
            for(Brod[] row : gameRecord.gameBoard)
            {
                response += gameRecord.getRow(row, 1) + "\n";
            }
        }
        else if(!isAdminUsername(username))
            response = "ERROR 00; Korisnik nije administrator.";
        else if(!isAdminPassword(password))
            response = "ERORR 00; Netocan password.";
        
        getBackResponse(response);
    }
    
    /***
     * Obrađuje administratorsku komandu 'NEW'. Ukoliko korisničko ime ili lozinka ne odgovaraju, vraća se poruka greške. 
     * U suprotnom serijalizira evidenciju te se kreira nova igra i vraća odgovor "OK". Ako nešto nije u redu sa serijalizacijom vraća se poruka greške.
     * @param username
     * @param password 
     */
    private void adminNew(String username, String password) {
        String response = "";
        
        if(isAdminUsername(username) && isAdminPassword(password))
        {
            String fileName = ServerSustava.config.dajPostavku("evidDatoteka");
            try 
            {
                Serijalizacija.serializeToFile(fileName, gameRecord);
                response = "OK";
                gameRecord.resetGame();
            } 
            catch (IOException ex) 
            {
                response = "ERROR 03; Greška u serijalizaciji.";
            }
            getBackResponse(response);
        }
        else
        {
            if(!isAdminUsername(username))
                response = "ERROR 00; Korisnik nije administrator.";
            else if(!isAdminPassword(password))
                response = "ERORR 00; Netocan password.";
            
            getBackResponse(response);
        }
    }
    
    /***
     * Obrađuje igračku komandu 'PLAY'. Dodaje igrača u igru te mu vraća podatke o ploči, trenutnom broju igrača i brodova, te koordinate njegovih
     * brodova. Ukoliko je korisnik već prijavljen u igru ili je igra već popunjena, vraća se poruka greške.
     * @param user - korisničko ime igrača
     */
    private void userPlay(String user) {
        String response = null;
        
        if(gameRecord.addPlayer(user))
        {
            int x = gameRecord.gameBoard[0].length;
            int y = gameRecord.gameBoard[1].length;
            int playersNum = gameRecord.users.size();
            int shipsNum = gameRecord.ships.size();
            
            String boardSize = x + "," + y;
            response = "Velicina ploce = " + boardSize + ", " 
                        + "broj igraca = " + playersNum + ", "
                        + "broj brodova = " + shipsNum + "\n";
            response += "Koordinate brodova = " + gameRecord.getUsersShips(user);
        }         
        else if(gameRecord.isPlayer(user))
            response = "ERROR 10: Korisnik " + user + " je vec prijavljen u igru.";
        else
            response = "ERROR 10: Igra je popunjena.";
        
        getBackResponse(response);
    }
    
    /***
     * Obrađuje igračku komandu [x,y]. Ako je igra počela provjerava se je li korisnik igrač i ima li 2 poteza više od ostalih igrača, ukoliko zadovoljava 
     * kriterije vraća mu se odgovor (iz zadatka: "0 - nije pogodio brod, 1 - pogodio broj (može se
     * pogoditi i vlastiti brod, ili brod koji je već potopljen), 2 /n) - mora čekati dok drugi igrači
     * ne odigraju svoj potez a n označava koliko igrača se čeka, 3 da su mu potopljeni svi
     * brodovi i nema prvo dalje igrati igru, 9 da je pobjednik igre"). 
     * @param user - korisničko ime
     * @param x - koordinata x
     * @param y - koordinata y
     */
    private void userAttack(String user, int x, int y) {
        if(gameRecord.canAttack(user) == 0)
        {
            if(gameRecord.gameStarted())
                getBackResponse(gameRecord.attack(user, x, y));
            else
                getBackResponse("ERROR 10; Igra ne moze poceti dok se svi igrace ne prijave.");
        }
        else 
            getBackResponse("2 (" + gameRecord.canAttack(user) + ")");
    }
    
    /***
     * Obrađuje igračku komandu 'STAT'. Ukoliko je korisnik prijavljen u igru i igra je završila, vraća podatke o igri (ploču, pozicije igrača, poteze).
     * U suprotnom vraća odgovarajuću poruku greške.
     * @param user - korisničko ime igrača.
     */
    private void userStat(String user) {
        String response = "LEGENDA: vlasnik broda <- ubojica\n";
        
        if(gameRecord.isPlayer(user))
        {
            if(gameRecord.isGameOver())
            {
                for(Brod[] row : gameRecord.gameBoard)
                {
                    response += gameRecord.getRow(row, 1) + "\n";
                }
                getBackResponse(response);
            }
            else 
                getBackResponse("ERROR 10; Igra nije zavrsila");
        }
        else 
            getBackResponse("ERROR 10; Korisnik nije prijavljen u igru.");
    }
  
}
