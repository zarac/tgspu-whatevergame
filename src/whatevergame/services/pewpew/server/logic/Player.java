/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package whatevergame.services.pewpew.server.logic;

import whatevergame.server.Client;

/**
 *
 * @author anderswik
 */
public class Player {
    private Client client;
    private int id;
    private boolean dead;
    private Gun gun;
    private int round;
    protected boolean ready;
    
    public Player(Gun gun, Client client){
        this.client = client;
        this.gun = gun;
        this.id = client.getSessionId();
        dead = false;
        round = 0;
    }
    
    public void spinChamber(){
        gun.spinChamber();
    }
    
    public void fireGun(){
        if(dead == false){
            dead = gun.fireGun();
        }
        if(!dead){ 
            round++; 
        }
    }
    
    public boolean isDead() {
        return dead;
    }

    public int getID() {
        return id;
    }

    public int getRound() {
        return round;
    }

    /**
     * Determines if this instance is ready.
     *
     * @return The ready.
     */
    public boolean isReady()
    {
        return this.ready;
    }

    /**
     * Sets whether or not this instance is ready.
     *
     * @param ready The ready.
     */
    public void setReady(boolean ready)
    {
        this.ready = ready;
    }

    public Client getClient() {
        return client;
    }
    
    public void setToDead() {
        dead = true;
    }
    
    
    
}
