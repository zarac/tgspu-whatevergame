/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package whatevergame.services.pewpew.server.logic;

import java.util.Random;

import logging.Logger;

/**
 *
 * @author anderswik
 */
public class Gun {
 private Random random;
 private int nbrOfChambers;
 private int bulletPos;
 private int currentPos;
 
    /**
     * A logger, it's handy to have.
     */
    protected Logger logger;
 
 public Gun(int chambers){
     random = new Random();
     currentPos = 0;
     nbrOfChambers = chambers;
     bulletPos = initBulletPos();
     logger = new Logger(this);
 }
 
 public int initBulletPos(){
     System.out.println("Init bullet pos"+bulletPos);
     return random.nextInt(nbrOfChambers);
 }
 
 public void spinChamber(){
     currentPos = random.nextInt(nbrOfChambers);
     logger.debug("current pos"+currentPos);
 }
 
 public boolean fireGun()
 {
     if(++currentPos > nbrOfChambers)
         currentPos = 0;

     logger.debug("fireGun() : current pos=" + currentPos + ", bulletPos=" + bulletPos);

     if (currentPos == bulletPos)
     {
         logger.debug("HIT! returning true");
         return true;
     }
     else
     {
         logger.debug("MISS! returning false");
         return false;
     }
 }
 
 public static void main(String[] args){
     Gun gun = new Gun(6);
     
     for(int i = 0; i < 100; i++){
         System.out.println("Fire gun, dead:"+gun.fireGun());
     }
 }
 
}
