/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package whatevergame.services.fivepad.server.logic;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author wiik
 */
public class Player {
    /**
     * List objekt med spelarens alla valda positioner.
     * nbrOfWins är antalet omgångar spelaren vann.
     */
    private List <Integer> list = new LinkedList();
    private int nbrOfWins = 0;
    private int id;
    
    public Player(){
        this(-1);
    }
    
    public Player(int id){
        this.id = id;
    }
    /**
     * Returnera spelarens id
     *
     * @param i     vilken position som ska läggas till.
     */
    public int getId(){
        return id;
    }
    /**
     * Lägger till position i Listan
     *
     * @param i     vilken position som ska läggas till.
     */
    public void addNext(int i){
        list.add(i);
    }
    /**
     * Returnerar listan med positioner.
     *
     * @return      instans variabel list.
     */
    public List getArr(){
        return list;
    }
    /**
     * Plus ett på nbrOfWins variabeln
     */
    public void addToWins(){
        nbrOfWins++;
    }
    /**
     * Plus ett på nbrOfWins variabeln
     */
    public int getNbrOfWins(){
        return nbrOfWins;
    }
    
    public String toString(){
        return ""+id;
    }
}
