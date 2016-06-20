/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.zadaca_1;

import java.io.Serializable;
import org.foi.nwtis.dkopic2.zadaca_1.Brod;
import java.util.ArrayList;
import org.foi.nwtis.dkopic2.zadaca_1.ServerSustava;


/***
 * Klasa koja implementira logiku i podatke jedne igre.
 * @author domagoj
 */
public class Evidencija implements Serializable{
    public ArrayList<String> users = new ArrayList<>();
    public ArrayList<Brod> ships = new ArrayList<>();
    public ArrayList<Potez> moves = new ArrayList<>();
    public Brod[][] gameBoard;
    
    /***
     * Resetira igru: briše brodove, korisnike i poteze. 
     * Generira novu ploču i brodove.
     */
    public void resetGame() {
        ships.clear();
        users.clear();
        moves.clear();
        createBoard();
        generateShips();
    }
    
    /***
     * Provjerava se je li korisnik već prijavljen u igru (nalazi se u popisu igrača).
     * @param player - korisničko ime
     * @return - true ukoliko je prijavljen, inače false
     */
    public boolean isPlayer(String player) {
        return users.contains(player);
    }
   
    /***
     * Dodaje korisnika u popis igrača i pridružuje mu brodove ukoliko se već ne nalazi i ima još mjesta u igri.
     * @param player - korisničko ime
     * @return - true ukoliko je uspješno dodan, inače false
     */
    public boolean addPlayer(String player) {
        int playersAllowed = Integer.parseInt(ServerSustava.config.dajPostavku("brojIgraca"));
        
        if(!users.contains(player))
        {
            if(users.size() < playersAllowed)
            {
                users.add(player);
                int playerNum = users.size();
                
                for(Brod ship : ships)
                {
                    if(ship.user.equals(Integer.toString(playerNum)))
                        ship.user = player;
                }
                                        
                return true;
            }
        }
        return false;
    }
    
    /***
     * Provjerava je li igra započela (svi korisnici su se prijavili u igru) i zapisuje početno stanje ploče u tom slučaju.
     * @return - true ukoliko je igra započela, inače false
     */
    public boolean gameStarted() {
        int usersNum = Integer.parseInt(ServerSustava.config.dajPostavku("brojIgraca"));
        
        if(users.size() == usersNum)
        {
            int x = Integer.parseInt(ServerSustava.config.dajPostavku("brojX"));
            int y = Integer.parseInt(ServerSustava.config.dajPostavku("brojY"));
            
            return true;
        }
        return false;
    }
    
    /***
     * Generira pozicije brodova i pridružuje redne brojeve igrača.
     */
    public void generateShips() {
        int shipNumber = Integer.parseInt(ServerSustava.config.dajPostavku("brojBrodova"));
        int playersNum = Integer.parseInt(ServerSustava.config.dajPostavku("brojIgraca"));
        
        for (int i = 1; i <= playersNum ; i++)
        {
         for (int j = 0; j < shipNumber; j++)
            { 
                while(true)
                {
                    int row = (int) Math.floor(Math.random() * gameBoard[0].length);
                    int col = (int) Math.floor(Math.random() * gameBoard[1].length);

                    if(gameBoard[row][col] == null)
                    {
                        Brod ship = new Brod(row,col,Integer.toString(i),false);
                        ships.add(ship);
                        gameBoard[row][col] = ship;
                        break;
                    } 
                }
            }   
        }
    }
    
    /***
     * Kreira igračku ploču prema postavkama igre.
     */
    public void createBoard() {
         int x = Integer.parseInt(ServerSustava.config.dajPostavku("brojX"));
         int y = Integer.parseInt(ServerSustava.config.dajPostavku("brojY"));
         gameBoard = new Brod[x][y];
         
         for (int i = 0 ; i < x; i++)
             for (int j = 0; j < y; j++)
                 gameBoard[i][j] = null;
    }

    /***
     * Dohvaća koordinate brodova korisnika.
     * @param user - korisničko ime
     * @return - ispis koordinata
     */
    public String getUsersShips(String user) {
        String coordinates = "";
        int x,y;
        
        for(Brod ship : ships)
        {
            if(ship.user.equals(user))
            {
                x = ship.x + 1;
                y = ship.y + 1;
                coordinates += "[" + x + "]" + "[" + y + "]  ";
            }
        }
        
        return coordinates;
    }
    
    /***
     * Pogađa poziciju na ploči. Ukoliko je korisnik igrač vraća mu se odgovor "OK" i zatim odgovarajuće poruke ovisno je li pogođen brod ili ne.
     * Ukoliko korisnik nije prijavljen u igru ili nije pogodio koordinatu koja postoji vraća mu se poruka greške.
     * @param user - korisničko ime 
     * @param x - koordinata x
     * @param y - koordinata y
     * @return - prigodni odgovor
     */
    public String attack(String user, int x, int y) {
        x = x - 1;
        y = y - 1;
        String response = "";
        Potez move = null;
        int xCord = Integer.parseInt(ServerSustava.config.dajPostavku("brojX")) - 1;
        int yCord = Integer.parseInt(ServerSustava.config.dajPostavku("brojY")) - 1;
        
        if(x <= xCord && y <= yCord)
        {
            if(isPlayer(user))
            {
                response = "OK ";

                if(isDead(user))
                    response += "{3}";
                else if(isWinner(user))
                    response += "{9}";
                else
                {
                    if(gameBoard[x][y] == null)
                    {
                        move = new Potez(user, x, y, false);
                        moves.add(move);
                        response += "{0}";
                    }
                    else if(gameBoard[x][y].isKilled)
                    {
                        move = new Potez(user, x, y, false);
                        moves.add(move);
                        response += "{1} [Brod je vec pogoden]";
                    }
                    else if(!gameBoard[x][y].isKilled)
                    {
                        gameBoard[x][y].isKilled = true;
                        move = new Potez(user, x, y, true);
                        moves.add(move);

                        if(isWinner(user))
                            response += "{9}";
                        else 
                            response += "{1}";

                        if(gameBoard[x][y].user.equals(user))
                            response += " [Pogoden je vlastiti brod]";
                    }
                }
            }
            else 
                response = "ERROR 10; Korisnik nije prijavljen u igru.";
        }
        else
        {
           response = "ERROR 10; Koordinata ne postoji.";
           move = new Potez(user, x, y, false);
           moves.add(move); 
        }
         
        return response;
    }
    
    /***
     * Provjerava je li korisnik 'mrtav' (svi brodovi su mu potopljeni).
     * @param user - korisničko ime
     * @return - true ukoliko su mu svi brodovi potopljeni, inače false
     */
    private boolean isDead(String user) {
        for(Brod ship : ships)
        {
            if(ship.user.equals(user))
                if(!ship.isKilled)
                    return false;
        }
        
        return true;
    }
    
    /***
     * Provjerava je li korisnik pobjednik (svi tuđi brodovi su potopljeni).
     * @param user - korisničko ime
     * @return - true ukoliko je pobjednik, inače false
     */
    public boolean isWinner(String user) {
        for(Brod ship : ships)
        {
            if(!ship.user.equals(user))
            {
                if(!ship.isKilled)
                    return false;
            }
        }
        
        return true;
    }
    
    /***
     * Vraća broj poteza korisnika.
     * @param user - korisničko ime
     * @return - broj poteza
     */
    private int getUserMovesCount(String user) {
        int counter = 0;
        
        for(Potez move : moves)
        {
            if(move.user.equals(user))
                counter ++;
        }
        
        return counter;
    }
    
    /***
     * Provjerava koliko je korisnik odigrao poteza više od preostalih igrača.
     * @param player - korisničko ime
     * @return - broj poteza
     */
    public int canAttack(String player) {
        int movesPlayed = getUserMovesCount(player);
        int usersWaiting = 0;
        
        for(String user : users)
        {
            if(!user.equals(player))
            {
               if(!isDead(user))
               {
                   if(movesPlayed > getUserMovesCount(user))
                       usersWaiting++;
               }
            }
        }
        
        return usersWaiting;
    }
    
    /***
     * Provjerava je li igra gotova (postoji li pobjednik).
     * @return - true ukoliko je igra završila, inače false
     */
    public boolean isGameOver() {
        for (String user : users)
        {
            if(isWinner(user))
                return true;
        }
        
        return false;
    }
    
    /***
     * Dohvaća red ploče, ako je status 1 tada prikazuje poteze igrača.
     * @param row - polje brodova
     * @return - redak igračke ploče
     */
    public String getRow(Brod[] row, int status) {
        String boardRow = "";
        String killedBy = "";
        
        for(Brod ship : row)
        {
            if(ship == null)
                boardRow += "[ PRAZNO POLJE ]\t"; 
            else
            {
                if(ship.isKilled && status == 1)
                {
                    killedBy = playedBy(ship.x, ship.y);
                    boardRow += "[" + ship.user + "<-" + killedBy + "]\t"; 
                }
                else
                {
                    if(ship.user.matches("\\d+"))
                        boardRow += "[    IGRAC " + ship.user + "   ]" + "\t";
                    else 
                        boardRow += "[    " + ship.user + "    ]\t";
                }
            }
        }
        
        return boardRow;
    }
    
    /***
     * Vraća korisnika koji je odigrao potez na odgovarajućoj koordinati.
     * @param x - koordinata x
     * @param y - koordinata y
     * @return - korisnik
     */
    private String playedBy(int x, int y) {
        String user = "";
        
        for(Potez move : moves) 
        {
            if(move.x == x && move.y == y)
                user = move.user;
        }
        return user;
    }
}
