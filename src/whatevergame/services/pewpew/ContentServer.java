/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package whatevergame.services.pewpew;

/**
 *
 * @author anderswik
 */
public class ContentServer extends whatevergame.services.Content {
    private boolean dead;
    private int score;
    private boolean turn;
    private boolean waitForPlayers;
    private boolean isAllDead;

    public ContentServer(boolean dead, int score, boolean turn,
                         boolean waitForPlayers, boolean isAllDead){
        this.dead = dead;
        this.score = score;
        this.turn = turn;
        this.waitForPlayers = waitForPlayers;
        this.isAllDead = isAllDead;
    }
    
    public boolean isDead() {
        return dead;
    }

    public boolean isAllDead() {
        return isAllDead;
    }

    public int getScore() {
        return score;
    }

    public boolean isTurn() {
        return turn;
    }

    public boolean isWaitForPlayers() {
        return waitForPlayers;
    }
    
    public String toString(){
        return "dead: "+dead+" score: "+score+" turn: "+turn+" waitForPlayer "+waitForPlayers+" isAllDead: "+isAllDead;
    }
    
}
