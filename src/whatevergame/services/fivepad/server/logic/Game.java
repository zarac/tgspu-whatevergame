/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package whatevergame.services.fivepad.server.logic;

import java.util.ArrayList;

/**
 *
 * @author anderswik
 */
public class Game {
    private Board board;
    private int roomNumber;
    private int numberOfPlayers;
    private int maximumNumber;
    private ArrayList <Integer> players;
    private int currentPlayer;
    private boolean gameOn;
    
    public Game(int roomNumber){
        this.roomNumber = roomNumber;
        currentPlayer = 0;
        board = new Board();
        this.numberOfPlayers = 0;
        this.maximumNumber = 2;
        players = new ArrayList<Integer>();
        gameOn = false;
    }
    
    public String wantToPlay(int clientSessionId){
        
        String value = "You are ";
        if(numberOfPlayers < maximumNumber && !isPlayer(clientSessionId)){
                players.add(clientSessionId);
                numberOfPlayers++;
                value = "added";
                board.addPlayer(clientSessionId);
            } else {
                value = "not added";
            }
        
        if(players.size() == maximumNumber){
            restart();
        }
        
        return value;
    }
    
    public boolean tryToMakeMove(int clientSessionId, String content){
        boolean check = false;
        if(gameOn){
            if(players.get(currentPlayer).intValue() == clientSessionId){
                if(board.makeMove(clientSessionId, content)){
                    check = true;
                }    
            }
        }
        return check;
    }
    
    public void iterateCurrentPlayer(){
        if(currentPlayer < (maximumNumber-1)){
           currentPlayer++; 
        } else {
            currentPlayer = 0;
        }
    }
    
    public int getCurrentClientID(){
        
        return players.get(currentPlayer);
    }
    
    public boolean getWinner(){
        return board.getWinner();
    }
    
    public boolean isPlayer(int client) {
        boolean check = false;
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).intValue() == client){
                check = true;
                gameOn = false;
            }
        }
        return check;
    }
    
    public boolean isPlayerPresent(int client) {
        boolean check = false;
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).intValue() == client){
                check = true;
            }
        }
        return check;
    }
    
    public boolean isFull() {
        boolean check = true;
        if(numberOfPlayers < maximumNumber){
            check = false;
        }
        return check;
    }
    
    public boolean amIFirst(int clientID){
        boolean check = false;
        if(players.get(0) == clientID){
            check = true;
        }
        return check;
    }
    
    public void restart(){
        gameOn = true;
        board = new Board();
        currentPlayer = 0;
        for(int i = 0; i < players.size(); i++){
            board.addPlayer(players.get(i).intValue());
        }
    }
    
    public void printYourPlayers(){
        for(int i = 0; i < players.size(); i++){
            System.out.println("Room Number:"+roomNumber+" Player:"+players.get(i).toString());
        }
    }
    
    public int whoIsMyBuddy(int id){
        int buddy = -1;
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).intValue() != id){
                buddy = players.get(i).intValue();
            }
        }
        return buddy;
    }
    
    
    public static void main(String[] args){
        Game game = new Game(1);
        System.out.println(game.wantToPlay(12));
        System.out.println(game.wantToPlay(10));
        
        System.out.println(game.wantToPlay(12));
        System.out.println(game.wantToPlay(10));
        
        System.out.println("try to make move"+game.tryToMakeMove(12, "1"));
        game.iterateCurrentPlayer();
        System.out.println("Get winner"+game.getWinner());
        System.out.println("try to make move"+game.tryToMakeMove(10, "4"));
        game.iterateCurrentPlayer();
        System.out.println("Get winner"+game.getWinner());
        System.out.println("try to make move"+game.tryToMakeMove(12, "2"));
        game.iterateCurrentPlayer();
        System.out.println("Get winner"+game.getWinner());
        System.out.println("try to make move"+game.tryToMakeMove(10, "5"));
        game.iterateCurrentPlayer();
        System.out.println("Get winner"+game.getWinner());
        System.out.println("try to make move"+game.tryToMakeMove(12, "3"));
        game.iterateCurrentPlayer();
        System.out.println("Get winner"+game.getWinner());
        //game.restart();
        game.wantToPlay(12);
        
        System.out.println("try to make move"+game.tryToMakeMove(12, "1"));
        game.iterateCurrentPlayer();
        System.out.println("Get winner"+game.getWinner());
        System.out.println("try to make move"+game.tryToMakeMove(10, "4"));
        game.iterateCurrentPlayer();
        System.out.println("Get winner"+game.getWinner());
        System.out.println("try to make move"+game.tryToMakeMove(12, "2"));
        game.iterateCurrentPlayer();
        System.out.println("Get winner"+game.getWinner());
        System.out.println("try to make move"+game.tryToMakeMove(10, "5"));
        game.iterateCurrentPlayer();
        System.out.println("Get winner"+game.getWinner());
        System.out.println("try to make move"+game.tryToMakeMove(12, "3"));
        game.iterateCurrentPlayer();
        System.out.println("Get winner"+game.getWinner());
    }
}
