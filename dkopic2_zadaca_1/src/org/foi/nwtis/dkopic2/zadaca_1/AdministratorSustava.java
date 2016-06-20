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
 * Klasa za rad administratora sustava.
 * @author domagoj
 */
public class AdministratorSustava {
    private String regexAdmin = "^-admin -s ((([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])) -port ([89][0-9]{3}) -u ([A-Za-z0-9_,-]*) -p ([A-Za-z0-9_,#,!,-]*) -(pause|start|stop|stat|new)$";
    private Pattern pattern;
    private Matcher matcher;
    private String address;
    private int port;
    private String user;
    private String password;
    private String adminCommand;

    /***
     * Ukoliko su komande ispravne, preuzimaju se podaci iz komande te šalje se zahtjev serveru.
     * U suprotnom se ispisuje poruka greške.
     * @param command - komanda administratora
     */
    public AdministratorSustava(String command) {
        
        if(validateParams(command))
        {
            address = matcher.group(1);
            port = Integer.parseInt(matcher.group(5));
            user = matcher.group(6);
            password = matcher.group(7);
            adminCommand = matcher.group(8).toUpperCase();
            
            sendRequest();
        }
        else System.out.println("ERROR 90; Neispravni argumenti!");
    }
    
    /***
     * Administrator se spaja na server socket, šalje mu komande te ispisuje odgovor servera.
     */
    public void sendRequest() {  

        try 
        {
            Socket socket = new Socket(address,port);
            System.out.println("Admin spojen na " + socket.getRemoteSocketAddress());

            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            String command = "USER " + user + "; PASSWD " + password + "; " + adminCommand + ";";
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
     * Provjerava se ispravnost administratorskih komandi.
     * @param command - komanda
     * @return - true ukoliko komanda odgovara regexu, inače false
     */
    public boolean validateParams(String command) {
        pattern = Pattern.compile(regexAdmin);
        matcher = pattern.matcher(command);
        
        return matcher.matches();
    }
}
