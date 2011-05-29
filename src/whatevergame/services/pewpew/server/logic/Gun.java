/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package whatevergame.services.pewpew.server.logic;

import java.util.Random;

/**
 *
 * @author anderswik
 */
public class Gun {
 private Random random;
 private int nbrOfChambers;
 private int bulletPos;
 private int currentPos;
 private boolean boom;
 
 public Gun(int chambers){
     random = new Random();
     boom = false;
     currentPos = 0;
     nbrOfChambers = chambers;
     bulletPos = initBulletPos();
 }
 
 public int initBulletPos(){
     System.out.println("Init bullet pos"+bulletPos);
     return random.nextInt(nbrOfChambers);
 }
 
 public void spinChamber(){
     currentPos = random.nextInt(nbrOfChambers);
     System.out.println("current pos"+currentPos);
 }
 
 public boolean fireGun(){
     currentPos++;
     if(currentPos == bulletPos){
         boom = true;
     } else {
         boom = false;
     }

     if(currentPos > nbrOfChambers)
     {
         currentPos = 0;
     }

     return boom;
 }
 
 public static void main(String[] args){
     Gun gun = new Gun(6);
     
     for(int i = 0; i < 100; i++){
         System.out.println("Fire gun, dead:"+gun.fireGun());
     }
 }
 
}
