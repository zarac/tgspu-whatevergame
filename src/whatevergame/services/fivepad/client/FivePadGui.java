/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package whatevergame.services.fivepad.client;

/**
 *
 * @author alfons
 */
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import whatevergame.services.fivepad.Content;
import whatevergame.services.pewpew.client.FrameTemp;

public class FivePadGui extends JPanel {
    /*Instance Variables*/
//    private JFrame window = new JFrame("Tic-Tac-Toe");

    private Client client;
//    private JButton button1 = new JButton("");
//    private JButton button2 = new JButton("");
//    private JButton button3 = new JButton("");
//    private JButton button4 = new JButton("");
//    private JButton button5 = new JButton("");
//    private JButton button6 = new JButton("");
//    private JButton button7 = new JButton("");
//    private JButton button8 = new JButton("");
//    private JButton button9 = new JButton("");
    private JButton buttonJoin;
    private JButton[] buttonArray;
//    private String letter = "";
//    private int count = 0;
//    private boolean win = false;
    private MyListener myListener;
    private JPanel panelMain, panelBoard, panelButton;
    private JLabel labelStatus;
    private ArrayList<Integer> arrayActiveButtons;
//    private FrameTemp tempFrame;
//    private JFrame frame;

//    public FivePadGui(Client p_client) {
        /*Create Window*/
//    window.setSize(300,300);
//    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    window.setLayout(new GridLayout(3,3));
    public FivePadGui(Client p_client) {

        buttonArray = new JButton[9];
        arrayActiveButtons = new ArrayList<Integer>();
        labelStatus = new JLabel("LABELZ");
        buttonJoin = new JButton("join");
//        frame = new JFrame();
//        tempFrame = new FrameTemp();
        client = p_client;
        panelBoard = new JPanel(new GridLayout(3, 3));
        panelMain = new JPanel(new BorderLayout());
        panelButton = new JPanel();
        myListener = new MyListener();
        
//        panelMain = new JPanel();
        
        
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 4, Toolkit.getDefaultToolkit().getScreenSize().height / 4));

        
        System.out.println(buttonArray.length);
        
        for (int i = 0; i<buttonArray.length; i++){
            
            buttonArray[i] = new JButton();
            
            buttonArray[i].addActionListener(myListener);
            panelBoard.add(buttonArray[i]);
            arrayActiveButtons.add(i);
            
        }
        
//        /*Add Buttons To The Window*/
//        panelBoard.add(button0);
//        panelBoard.add(button1);
////        arrayActiveButtons.add();
//        panelBoard.add(button2);
//        panelBoard.add(button3);
//        panelBoard.add(button4);
//        panelBoard.add(button5);
//        panelBoard.add(button6);
//        panelBoard.add(button7);
//        panelBoard.add(button8);
        
        
        
        
        /* Add Button To The ButtonPanel*/
        panelButton.add(buttonJoin); 
        panelButton.add(labelStatus); 
        

        /*Add The Action Listener To The Buttons*/
//        myListener = new MyListener();
//        button1.addActionListener(myListener);
//        button2.addActionListener(myListener);
//        button3.addActionListener(myListener);
//        button4.addActionListener(myListener);
//        button5.addActionListener(myListener);
//        button6.addActionListener(myListener);
//        button7.addActionListener(myListener);
//        button8.addActionListener(myListener);
//        button9.addActionListener(myListener);
        buttonJoin.addActionListener(myListener);


//        this.setPreferredSize(new Dimension(300,300));
        panelMain.add(panelBoard, BorderLayout.CENTER);
        panelMain.add(panelButton, BorderLayout.SOUTH); 
//        panelButton.add(buttonJoin); 
//        panelMain.add(panelBoard);
        this.add(panelMain, BorderLayout.CENTER);
//        this.add(panelMain);
    }

    
    public void madeMove(int p_move){
        arrayActiveButtons.remove(new Integer(p_move));
        buttonArray[p_move].setEnabled(false);
    }
    
//    public void disableButtons(){
//        for (int i = 0; i<buttonArray;)
//    }
    
    public void enableButtons(boolean p_isActive){
        for (int i = 0; i<arrayActiveButtons.size(); i++){
            buttonArray[arrayActiveButtons.get(i)].setEnabled(p_isActive);
        }
    }
    
//    public void toggleRemainingBoard(){
//        
//    }
    
    public void setLabelText(String p_inString){
        labelStatus.setText(p_inString);
    }
    
    private class MyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent a) {

            /*Display X's or O's on the buttons*/
//            if (a.getSource() == button1) {
            if (a.getSource() == buttonArray[0]) {
                client.send(new Content(("1")));
//                button1.setText(letter);
//                button1.setEnabled(false);
            } else if (a.getSource() == buttonArray[1]) {
                client.send(new Content(("2")));
//                button2.setText(letter);
//                button2.setEnabled(false);
            } else if (a.getSource() == buttonArray[2]) {
                client.send(new Content(("3")));
//                button3.setText(letter);
//                button3.setEnabled(false);
            } else if (a.getSource() == buttonArray[3]) {
                client.send(new Content(("4")));
//                button4.setText(letter);
//                button4.setEnabled(false);
            } else if (a.getSource() == buttonArray[4]) {
                client.send(new Content(("5")));
//                button5.setText(letter);
//                button5.setEnabled(false);
            } else if (a.getSource() == buttonArray[5]) {
                client.send(new Content(("6")));
//                button6.setText(letter);
//                button6.setEnabled(false);
            } else if (a.getSource() == buttonArray[6]) {
                client.send(new Content(("7")));
//                button7.setText(letter);
//                button7.setEnabled(false);
            } else if (a.getSource() == buttonArray[7]) {
                client.send(new Content(("8")));
//                button8.setText(letter);
//                button8.setEnabled(false);
            } else if (a.getSource() == buttonArray[8]) {
                client.send(new Content(("9")));
//                button9.setText(letter);
//                button9.setEnabled(false);
            }
            else if (a.getSource() == buttonJoin){
                client.send(new Content("WantToPlay"));
            }
        }
    }
}
