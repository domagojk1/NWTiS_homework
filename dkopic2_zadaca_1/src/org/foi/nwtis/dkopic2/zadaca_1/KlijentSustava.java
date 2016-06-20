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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/***
 * Klasa za rad klijenta sustava.
 * @author domagoj
 */
public class KlijentSustava {
    private String regexUser = "^-user -s ((([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])) -port ([89][0-9]{3}) -u ([A-Za-z0-9_,-]*) ((-x (10|[1-9]) -y (10|[1-9]))|-stat|-play)";
    private int port;
    private String user;
    private String address;
    private String userCommand;
    private Pattern pattern;
    private Matcher matcher;

    /***
     * Ukoliko su parametri ispravni, učitava određene podatke te šalje zahtjev serveru.
     * @param command 
     */
    public KlijentSustava(String command) {
        if(validateParams(command))
        {
            address = matcher.group(1);
            port = Integer.parseInt(matcher.group(5));
            user = matcher.group(6);
            
            if(matcher.group(8) == null)
            {
                userCommand = matcher.group(7).substring(1).toUpperCase();
            }
            else
            {
                userCommand = "" + matcher.group(9) + "," + matcher.group(10);
            }
                  
            sendRequest();
        }
        else System.out.println("ERROR 90; Neispravni argumenti!");
    }
    
    /***
     * Spaja se na server socket te šalje komande serveru, naposljetku ispisuje odgovor od servera.
     */
    private void sendRequest() {
        try 
        {
            Socket socket = new Socket(address,port);
            System.out.println("User spojen na " + socket.getRemoteSocketAddress());

            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            
            String command = "USER " + user + "; " + userCommand + ";";
            output.write(command.getBytes());
            output.flush();
            socket.shutdownOutput();

            StringBuilder response = new StringBuilder();
            int data;

            while((data = input.read()) != -1)
            {
                response.append((char) data);
            }

            System.out.println("Odgovor od servera:\n" + response);

            input.close();
            output.close();
        } 
        catch (IOException ex) 
        {
            System.out.println("ERROR: Server nije aktivan.");
        }
    }
    
    /***
     * Provjerava ispravnost primljenih komandi.
     * @param command - komanda klijenta
     * @return - true ako komande odgovaraju regexu, inače false
     */
    public boolean validateParams(String command) {
        pattern = Pattern.compile(regexUser);
        matcher = pattern.matcher(command);
        
        return matcher.matches();
    }
}
