/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package whatevergame.services.fivepad.server.logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author anderswik
 */
public class Board {
    private boolean winner;
    private List <Integer> list;
    private ArrayList <Player> players;
    private String currentBoard;
    private int pos;
    
    
    public Board(){
        initList();
        players = new ArrayList<Player>();
        winner = false;
        currentBoard = "";
        pos = 0;
    }
    
    public boolean getWinner(){
        return winner;
    }
    
    public void addPlayer(int id){
        System.out.println(players.size());
        if(players.size() < 2){
            players.add(new Player(id));
        }
    }
    
    public Player whoIsPlayer(int id){
        Player player = null;
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getId() == id){
                player = players.get(i);
            }
        }
        return player;
    }
    
    /**
     * initList metoden initerar list med 9 positioner, samma antal som
     * positioner på brädet.
     */
    public void initList(){
        list = new LinkedList();
        for(int i = 1; i < 9; i++){
            list.add(i);
        }
    }
    
    /**
     * playerMove tar bort den valda positionen från listan med de lediga positionerna.
     * Skickar den valda positionen till listan i player objektet.
     * Sätter positionen i viewern som tagen.
     * Kontrollerar om draget var ett vinnande drag.
     * Om det var ett vinnande drag sätts vinnarens namn till viewern.
     *
     * @param i     positionen som spelaren valde.
     * @param s     texten som ska skickas till viewern och sättas i knappen.
     * @param p     vilken spelare som gör draget.
     */
    public boolean playerMove(int i, String s, Player p){
        boolean check = false;
        
        for(int j = 0; j < list.size(); j++){
            if(list.get(j)==i){
                list.remove(j);
                check = true;
            }
        }
        p.addNext(i);
        winner = isWinner(p.getArr());
        return check;
    }
    
    public boolean makeMove(int id, String in){
        Player player = whoIsPlayer(id);
        boolean check = false;
        
        if(player != null && makeInt(in) != -1){
            if(id == players.get(0).getId()){
                check = playerMove(makeInt(in), "O", player);
                currentBoard += in+"O";
            } 
            else if(id == players.get(1).getId()){
                check = playerMove(makeInt(in), "X", player);
                currentBoard += in+"X";
            }
        }
        
        return check;
    }
    
    public int makeInt(String in){
        int temp = -1;
        
        try{
            temp = Integer.parseInt(in);
        }catch(NumberFormatException e){}
        
        return temp;
    }
    
    /**
     * isWinner kontrollerar om listan som skickas in innehåller
     * en sekvens som vinner spelet.
     *
     * @param l     listan med spelarens positioner.
     * @return      sant eller falskt
     */
    public boolean isWinner(List l){
        if(containsSek(1, 2, 3, l)){
            return true; }
        else if(containsSek(4, 5, 6, l)) {
            return true; }
        else if(containsSek(7, 8, 9, l)) {
            return true; }
        else if(containsSek(1, 4, 7, l)) {
            return true; }
        else if(containsSek(2, 5, 8, l)) {
            return true; }
        else if(containsSek(3, 6, 9, l)) {
            return true; }
        else if(containsSek(1, 5, 9, l)) {
            return true; }
        else if(containsSek(7, 5, 3, l)) {
            return true; }
        else{ 
            return false;}
    }
    /**
     * containSek kontrollerar om tre tal finns med i en lista.
     * Sedan returneras true eller falskt beroende på om alla
     * talen finns med.
     *
     * @param on    tal 1
     * @param tw    tal 2
     * @param th    tal 3
     * @param l     listan som talen kontrolleras mot.
     * @return      sant eller falskt
     */
    public boolean containsSek(int on, int tw, int th, List l){
        boolean one = false;
        boolean two = false;
        boolean thr = false;
        for(int i = 0; i < l.size(); i++){
            if(Integer.parseInt(l.get(i).toString())==on){
                one = true;
            }
            if(Integer.parseInt(l.get(i).toString())==tw){
                two = true;
            }
            if(Integer.parseInt(l.get(i).toString())==th){
                thr = true;
            }
        }
        if(one&&two&&thr){
            return true;
        }
        else{
            return false;
        }
    }

    public String getCurrentBoard() {
        return currentBoard;
    }
    
    public static void main(String[] args){
        Board board = new Board();
        board.addPlayer(1);
        board.addPlayer(2);
        
        board.makeMove(1, "1");
        board.makeMove(2, "4");
        board.makeMove(1, "2");
        board.makeMove(2, "5");
        board.makeMove(1, "3");
//        board.makeMove(2, "9");
        
        System.out.println(board.getWinner());
    }
    
}
