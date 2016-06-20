/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.zadaca_1;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/***
 * Klasa za pregled datoteke evidencije.
 * @author domagoj
 */
public class PregledSustava {
    private String regexShow = "^-show -s ([a-zA-Z0-9_.]*)";
    private String command;
    private Pattern pattern;
    private Matcher matcher;
    
    /***
     * Ukoliko parametri odgovaraju, poziva metodu za prikaz podataka evidencije, u suprotnom vraća poruku greške.
     * @param command - komanda za pregled sustava
     */
    public PregledSustava(String command){
        this.command = command;
        
        if(validateParams())
        {
            getData();
        }
        else 
            System.out.println("ERROR: Argumenti ne odgovaraju.");
    }
    
    /***
     * Deserijalizira evidenciju te ispisuje početno stanje ploče, trenutno stanje ploče, poteze igrača te pobjednika ukoliko ga ima.
     */
    private void getData() {
        String fileName = matcher.group(1);
        System.out.println(fileName);
            try 
            {
                Evidencija record = Serijalizacija.deserializeFromFile(fileName);
                String startBoard = "Pocetno stanje ploce:\n";

                for (Brod[] row : record.gameBoard)
                {
                    startBoard += record.getRow(row, 0) + "\n";
                }
                System.out.println(startBoard);

                System.out.println("LEGENDA: vlasnik broda <- ubojica");

                String currentBoard = "Trenutno stanje ploce:\n";

                for (Brod[] row : record.gameBoard)
                {
                    currentBoard += record.getRow(row, 1) + "\n";
                }

                System.out.println(currentBoard);

                String moves = "Potezi igraca:\n";
                String isHit = "";

                for (Potez move : record.moves)
                {
                    if(move.shipKilled)
                        isHit = "-->" + "POGODAK";
                    else
                        isHit = "-->" + "PROMASAJ";
                    moves += move.user + " [" + move.x + "," + move.y + "]" + isHit + "\n";
                }

                System.out.println(moves);

                for (String user : record.users)
                {
                    if(record.isWinner(user))
                        System.out.println("POBJEDNIK IGRE: " + user);
                }
            } 
            catch (IOException ex) 
            {
                System.out.println("ERROR: Nešto nije u redu sa deserijalizacijom / datoteka ne postoji.");
            }
    }
    
    /***
     * Provjerava parametre za pregled sustava.
     * @return - true ukoliko parametri odgovaraju regexu, inače false.
     */
    public boolean validateParams() {
        pattern = Pattern.compile(regexShow);
        matcher = pattern.matcher(command);
        
        return matcher.matches();
    }
}
