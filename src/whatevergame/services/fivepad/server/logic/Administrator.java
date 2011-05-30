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
public class Administrator {
    private ArrayList <Game> games;
    
    public Administrator(){
        games = new ArrayList<Game>();
    }
    
    public String wantToPlay(int clientId){
        String answer = "";
        int position = findPlayer(clientId);
        if(position > -1 && position < games.size()){
            answer = games.get(position).wantToPlay(clientId);
        } else {
            answer = games.get(findRoom()).wantToPlay(clientId);
        }
        return answer;
    }
    
    public boolean tryToMakeMove(int clientId, String content){
        boolean check = false;
        for(int i = 0; i < games.size(); i++){
            if(games.get(i).isPlayerPresent(clientId)){
                check = games.get(i).tryToMakeMove(clientId, content);
            }
        }
        return check;
    }
    
    public boolean getWinner(int clientId){
        int position = findPlayer(clientId);
        System.out.println("Position: "+position);
        boolean check = false;
        if(position > -1 && position < games.size()){
            check = games.get(position).getWinner();
        }
        return check;
    }
    
    public int getCurrentClientID(int clientId){
        int position = findPlayer(clientId);
        if(position > -1 && position < games.size()){
            position = games.get(position).getCurrentClientID();
        }
        return position;
    }
    
    public int findRoom(){
        int position = -1;
        boolean check = true;
        for(int i = 0; i < games.size(); i++){
            if(!games.get(i).isFull()){
                position = i;
                check = false;;
            }
        }
        if(check){
            games.add(new Game(games.size()+1));
            position = games.size()-1;
        }
        return position;
    }
    
    public void iterateCurrentPlayer(int clientId){
        int position = findPlayer(clientId);
        if(position > -1 && position < games.size()){
            games.get(position).iterateCurrentPlayer();
        }
    }
    
    public boolean isPlayer(int clientId){
        boolean check = false;
        if(findPlayer(clientId) == -1){
            check = true;
        }
        return check;
    }
    
    public boolean amIFirst(int clientId){
        boolean check = false;
        int position = findPlayer(clientId);
        if(position > -1 && position < games.size()){
            check = games.get(position).amIFirst(clientId);
        }
        return check;
    }
    
    public int findPlayer(int clientId){
        int position = -1; 
        for(int i = 0; i < games.size(); i++){
            if(games.get(i).isPlayerPresent(clientId)){
                position = i;
            }
        }
        return position;
    }
    
    public void printAllRooms(){
        System.out.println("Start to print players");
        for(int i = 0; i < games.size(); i++){
            games.get(i).printYourPlayers();
        }
        System.out.println("end printing players");
    }
    
    public boolean restart(int clientId){
        boolean check = false;
        int position = findPlayer(clientId);
        if(position > -1 && position < games.size()){
            games.get(position).restart();
        }
        return check;
    }
    
    public int findBuddy(int id){
        int position = findPlayer(id);
        if(position > -1 && position < games.size()){
            position = games.get(position).whoIsMyBuddy(id);
        }
        return position;
    }
    
    
    public static void main(String[] args){
        Administrator game = new Administrator();
        System.out.println(game.wantToPlay(12));
        System.out.println(game.wantToPlay(10));
        
        System.out.println(game.wantToPlay(13));
        System.out.println(game.wantToPlay(14));
        
        game.printAllRooms();
        
        System.out.println("try to make move"+game.tryToMakeMove(12, "1"));
        game.iterateCurrentPlayer(12);
        System.out.println("Get winner"+game.getWinner(12));
        System.out.println("try to make move"+game.tryToMakeMove(10, "4"));
        game.iterateCurrentPlayer(10);
        System.out.println("Get winner"+game.getWinner(10));
        System.out.println("try to make move"+game.tryToMakeMove(12, "2"));
        game.iterateCurrentPlayer(12);
        System.out.println("Get winner"+game.getWinner(12));
        System.out.println("try to make move"+game.tryToMakeMove(10, "5"));
        game.iterateCurrentPlayer(10);
        System.out.println("Get winner"+game.getWinner(10));
        System.out.println("try to make move"+game.tryToMakeMove(12, "3"));
        game.iterateCurrentPlayer(12);
        System.out.println("Get winner"+game.getWinner(12));
        game.restart(12);
        //game.wantToPlay(12);
        
        System.out.println();
        System.out.println();
        System.out.println("try to make move"+game.tryToMakeMove(13, "1"));
        game.iterateCurrentPlayer(13);
        System.out.println("Get winner"+game.getWinner(13));
        System.out.println("try to make move"+game.tryToMakeMove(14, "4"));
        game.iterateCurrentPlayer(14);
        System.out.println("Get winner"+game.getWinner(14));
        System.out.println("try to make move"+game.tryToMakeMove(13, "2"));
        game.iterateCurrentPlayer(13);
        System.out.println("Get winner"+game.getWinner(13));
        System.out.println("try to make move"+game.tryToMakeMove(14, "5"));
        game.iterateCurrentPlayer(14);
        System.out.println("Get winner"+game.getWinner(14));
        System.out.println("try to make move"+game.tryToMakeMove(13, "3"));
        game.iterateCurrentPlayer(13);
        System.out.println("Get winner"+game.getWinner(13));
        
        System.out.println();
        System.out.println();
        System.out.println("try to make move"+game.tryToMakeMove(12, "1"));
        game.iterateCurrentPlayer(12);
        System.out.println("Get winner"+game.getWinner(12));
        System.out.println("try to make move"+game.tryToMakeMove(10, "4"));
        game.iterateCurrentPlayer(10);
        System.out.println("Get winner"+game.getWinner(10));
        System.out.println("try to make move"+game.tryToMakeMove(12, "2"));
        game.iterateCurrentPlayer(12);
        System.out.println("Get winner"+game.getWinner(12));
        System.out.println("try to make move"+game.tryToMakeMove(10, "5"));
        game.iterateCurrentPlayer(10);
        System.out.println("Get winner"+game.getWinner(10));
        System.out.println("try to make move"+game.tryToMakeMove(12, "3"));
        game.iterateCurrentPlayer(12);
        System.out.println("Get winner"+game.getWinner(12));
        game.restart(12);
    }
}
