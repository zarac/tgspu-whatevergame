/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package whatevergame.services.pewpew.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author anderswik
 */
public class PewPewGui extends JPanel {

//    private ControllerMenu controllerMenu;
//    private ControllerPewPew client;
    private Client client;
    private JButton buttonShoot, buttonSpin, buttonQuit, /*ny*/ buttonQue;
    private JLabel labelBack, labelScore, labelPlayers;
    private JPanel panelButton, panelMain;
    private Dimension frameDimension, panelButtonDimension, buttonDimension, screenDimension;

    public PewPewGui(Client p_client) {
        this.client = p_client;

//        FrameTemp frameTemp = new FrameTemp();
        client.setGuiPewPew(this);
        this.setOpaque(false);
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createMatteBorder(5, 5, 0, 0, Color.GREEN));
        
        labelBack = new JLabel();
        client.setImageNormal();
        labelScore = new JLabel("labelScore");
        labelPlayers = new JLabel("labelPlayers");
        
        screenDimension = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        frameDimension = new Dimension (screenDimension.width/3*2,screenDimension.height/3*2);
        panelButtonDimension = new Dimension(frameDimension.width / 2, frameDimension.height / 5);
        buttonDimension = new Dimension(panelButtonDimension.width / 6, panelButtonDimension.height / 3);

        panelButton = new JPanel(new FlowLayout(0, 5, 10));
        panelButton.setOpaque(false);

        panelButton.setPreferredSize(panelButtonDimension);
        buttonShoot = new JButton("shoot");
        buttonSpin = new JButton("spin");
        buttonQuit = new JButton("quitz");
        buttonQue = new JButton("que");
        buttonShoot.setPreferredSize(buttonDimension);
        buttonSpin.setPreferredSize(buttonDimension);
        buttonQuit.setPreferredSize(buttonDimension);
        buttonQue.setPreferredSize(buttonDimension);


        // ActionListeners
        MyListener myListener = new MyListener();
        buttonShoot.addActionListener(myListener);
        buttonSpin.addActionListener(myListener);
        buttonQuit.addActionListener(myListener);
        buttonQue.addActionListener(myListener);


        // Lägger till knapparna till panelerna
        panelButton.add(buttonSpin);
        panelButton.add(buttonShoot);
        panelButton.add(buttonQuit);
        panelButton.add(buttonQue);
        panelButton.add(labelScore);
        panelButton.add(labelPlayers);
        this.add(panelButton, BorderLayout.SOUTH);
        this.add(labelBack, BorderLayout.CENTER);
    }

    public void toggleButtonActivity(boolean p_boolean) {
        buttonShoot.setEnabled(p_boolean);
        buttonSpin.setEnabled(p_boolean);
        buttonQue.setEnabled(p_boolean);
    }

    public void toggetButtonActivityGameOver() {
        buttonShoot.setEnabled(false);
        buttonSpin.setEnabled(false);
        buttonQue.setEnabled(true);
    }

    public void setImageToLabel(ImageIcon p_image) {
        labelBack.setIcon(p_image);
    }
    
    public void setLabelScore(String p_string){
        labelScore.setText(p_string);
        
    }

    public void setPlayers(String players)
    {
        labelPlayers.setText(players);
    }

    private class MyListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == buttonShoot) {
                System.out.println("Shoot");
                client.Shoot();
            }
            if (e.getSource() == buttonSpin) {
                System.out.println("Spin");
                client.Spin();
            }
            if (e.getSource() == buttonQue) {
                System.out.println("Que");
                client.Que();
            }
            if (e.getSource() == buttonQuit) {
                System.out.println("Exit");
                client.Exit();
            }
        }
    }
}
