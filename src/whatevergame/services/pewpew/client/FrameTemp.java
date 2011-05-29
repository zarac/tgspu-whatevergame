/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package whatevergame.services.pewpew.client;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author anderswik
 */
public class FrameTemp extends JFrame{
   
    Dimension dimensionFrame;
   
    public FrameTemp(){
   
        Dimension dimensionScreen = Toolkit.getDefaultToolkit().getScreenSize();
 
//        this.setBackground(Color.white);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(dimensionScreen.width / 2 - (dimensionScreen.width / 3), dimensionScreen.height / 2 - (dimensionScreen.height / 3), dimensionScreen.width / 3 * 2, dimensionScreen.height / 3 * 2);
        dimensionFrame = new Dimension(dimensionScreen.width / 3 * 2, dimensionScreen.height / 3 * 2);
       
       
        this.setVisible(true);
}
   
   
        /* Calls updateUI on all sub-components of the JFrame */
    public void updateUI() {
        SwingUtilities.updateComponentTreeUI(this);
    }
   
   
        /**
     * @return the dimensionFrame
     */
    public Dimension getFrameDimensions() {
        return dimensionFrame;
    }
   
   
}