/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package whatevergame.services.pewpew.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author anderswik
 */
public class PewPewGui extends JPanel {

    private Client client;
    private JButton buttonShoot, buttonSpin, buttonQuit, /*ny*/ buttonReplay;
    private JLabel labelBack, labelScore, labelPlayers;
    private JPanel panelButton, panelMain;
    private Dimension frameDimension, panelButtonDimension, buttonDimension, screenDimension;

    public PewPewGui(Client p_client) {
        this.client = p_client;

        client.setGuiPewPew(this);
        this.setOpaque(false);
        this.setLayout(new BorderLayout());
        
        labelBack = new JLabel();
        client.setImageNormal();
        labelScore = new JLabel("labelScore");
        labelPlayers = new JLabel("labelPlayers");
        
        screenDimension = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        frameDimension = new Dimension (screenDimension.width/3*2,screenDimension.height/3*2);
        panelButtonDimension = new Dimension(frameDimension.width / 2, frameDimension.height / 5);
        buttonDimension = new Dimension(panelButtonDimension.width / 6, panelButtonDimension.height / 3);

        panelButton = new JPanel(new GridLayout(2,1));
        panelButton.setOpaque(false);

        panelButton.setPreferredSize(panelButtonDimension);
        buttonShoot = new JButton("shoot");
        buttonSpin = new JButton("spin");
        buttonQuit = new JButton("quitz");
        buttonReplay = new JButton("Replay");
        buttonShoot.setPreferredSize(buttonDimension);
        buttonSpin.setPreferredSize(buttonDimension);
        buttonQuit.setPreferredSize(buttonDimension);
        buttonReplay.setPreferredSize(buttonDimension);


        // ActionListeners
        MyListener myListener = new MyListener();
        buttonShoot.addActionListener(myListener);
        buttonSpin.addActionListener(myListener);
        buttonQuit.addActionListener(myListener);
        buttonReplay.addActionListener(myListener);


        // Lägger till knapparna till panelerna
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout());
        buttonPanel.add(buttonSpin);
        buttonPanel.add(buttonShoot);
        buttonPanel.add(buttonReplay);
        buttonPanel.add(buttonQuit);
        buttonPanel.setVisible(true);
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout());
        scorePanel.add(labelScore);
        scorePanel.add(labelPlayers);
        scorePanel.setVisible(true);
        panelButton.add(buttonPanel);
        panelButton.add(scorePanel);
        
        this.add(panelButton, BorderLayout.SOUTH);
        this.add(labelBack, BorderLayout.CENTER);
    }

    public void toggleButtonPlayingCurrentPlayer(){
        buttonShoot.setEnabled(true);
        buttonSpin.setEnabled(true);
        buttonReplay.setEnabled(false);
    }
    public void toggleButtonPlayingNotCurrentPlayer(){
        buttonShoot.setEnabled(false);
        buttonSpin.setEnabled(false);
        buttonReplay.setEnabled(false);
    }

//    public void toggleButtonActivity(boolean p_boolean) {
//        buttonShoot.setEnabled(p_boolean);
//        buttonSpin.setEnabled(p_boolean);
//        buttonReplay.setEnabled(p_boolean);
//    }

    public void toggleButtonGameOver() {
        buttonShoot.setEnabled(false);
        buttonSpin.setEnabled(false);
        buttonReplay.setEnabled(true);
    }
    public void toggleButtonNotStarted(){
        buttonShoot.setEnabled(false);
        buttonSpin.setEnabled(false);
        buttonReplay.setEnabled(false);
    }

    public void setImageToLabel(ImageIcon p_image) {
        labelBack.setIcon(p_image);
        labelBack.repaint();
    }
    
    public void setLabelScore(String p_string){
        labelScore.setText(p_string);
        
    }

    public void setLabelPlayers(String players)
    {
        labelPlayers.setText(players);
    }

    private class MyListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == buttonShoot) {
//                System.out.println("Shoot");
                client.shoot();
                toggleButtonPlayingNotCurrentPlayer();
            }
            if (e.getSource() == buttonSpin) {
//                System.out.println("Spin");
                client.spin();
            }
            if (e.getSource() == buttonReplay) {
//                System.out.println("Que");
                client.replay();
            }
            if (e.getSource() == buttonQuit) {
//                System.out.println("Exit");
                client.exit();
            }
        }
    }
}
