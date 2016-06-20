/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.zadaca_1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * Main klasa.
 * @author domagoj
 */
public class Zadaca_dkopic2_1 {
    
    public static void main(String args[]){
        String regex = "(^-server.+)|(^-admin.+)|(^-user.+)|(^-show.+)";
        String regexShow = "^-show -s ([a-zA-Z0-9_.]*)";
        
        StringBuilder builder = new StringBuilder();
    
        for (String arg : args) {
            builder.append(arg).append(" ");
        }
        
        String command = builder.toString().trim();
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(command);
        
        if(matcher.matches())
        {
            switch(args[0])
            {
                case "-server":
                    ServerSustava server = new ServerSustava(command);
                    break;

                case "-admin":
                    AdministratorSustava admin = new AdministratorSustava(command);
                    break;

                case "-user":
                    KlijentSustava user = new KlijentSustava(command);
                    break;

                case "-show":
                    PregledSustava show = new PregledSustava(command);
                    break;
            }   
        }
        else System.out.println("ERROR 90; Neispravni argumenti!");
    }
}
