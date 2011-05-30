package whatevergame.services.pewpew.client;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import whatevergame.services.ClientService;

import whatevergame.services.pewpew.Content;
import whatevergame.services.pewpew.StateContent;
import whatevergame.services.pewpew.State;
import whatevergame.services.pewpew.State.StatePlayer;

/**
 * The testing client. It responds to a received message.
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
public class Client extends ClientService {

    private ImageIcon[] arrLive, arrDead, arrNormal;
    private Random rand;
    private int randomInt = 0;
    private PewPewGui gui;
    private int myId;
    private State currentState, lastState;

    /**
     * {@inheritDoc}
     * @see ClientService#Client(int,Client)
     */
    public Client(int id, whatevergame.client.Client client) {
        super(id, client);
        rand = new Random();
//        gui = new GUI(this);
        populateArrays();

        gui = new PewPewGui(this);
//        gui.toggleButtonActivity(false);
//        super.getGui().add(gui);
    }

//    super.
    /**
     * {@inheritDoc}
     * @see ClientService#enable()
     */
    public void enable() {
        send(new Content(Content.PEWPEW_INIT));
    }

    @Override
    public JPanel getGui() {
        return gui;
    }

    // Från CPP
    public void populateArrays() {
        String pathLive, pathDead, pathNormal;
        arrLive = new ImageIcon[5];
        arrDead = new ImageIcon[8];
        arrNormal = new ImageIcon[2];

        for (int i = 0; i < arrLive.length; i++) {
            pathLive = "data/images/pewPewLive" + i + ".jpg";
            arrLive[i] = resizeImage(pathLive);
        }
        for (int i = 0; i < arrDead.length; i++) {
            pathDead = "data/images/pewPewDead" + i + ".jpg";
//            arrDead[i] = new ImageIcon(pathDead);
            arrDead[i] = resizeImage(pathDead);
        }
        for (int i = 0; i < arrNormal.length; i++) {
            pathNormal = "data/images/pewPewNormal" + i + ".jpg";
            arrNormal[i] = resizeImage(pathNormal);
        }

    }

    // Från CPP
    public ImageIcon resizeImage(String p_path) {
        // ToDo
        // ska ej vara hårdkodat utan följa framen storlek
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameDimensions = new Dimension(screenDimension.width / 3 * 2, screenDimension.height / 3 * 2);
        // Skapar en ny bild från en ImageIcon (av pathen)
        Image tempImage = new ImageIcon(p_path).getImage();
        // Scalar om bilden till rätt storlek
        tempImage = tempImage.getScaledInstance(frameDimensions.width / 2, frameDimensions.height / 5 * 4, java.awt.Image.SCALE_SMOOTH);
        // Rätt storlek ImageIcon redo att sättas in i framen
        ImageIcon scaledImage = new ImageIcon(tempImage);


        return scaledImage;
    }

    // Från CPP
    public void setImageLive() {
        randomInt = rand.nextInt(arrLive.length);
        gui.setImageToLabel(arrLive[randomInt]);
    }

    // Från CPP
    public void setImageNormal() {
        randomInt = rand.nextInt(arrNormal.length);
        gui.setImageToLabel(arrNormal[randomInt]);
    }

    // Från CPPP
    public void setImageDead() {
        randomInt = rand.nextInt(arrDead.length);
        gui.setImageToLabel(arrDead[randomInt]);
    }

    // Från CPP
    public void setGuiPewPew(PewPewGui p_pewPew) {
        this.gui = p_pewPew;
    }

    // TODO : Shouldn't need to cast
    @Override
    public void receive(whatevergame.services.Content p_content) {


        if (p_content instanceof Content) {
            Content content = (Content) p_content;
            if (content.getCommand() == Content.PLAYER_ID) {
                myId = content.getPlayerId();
            }
        } else if (p_content instanceof StateContent) {
            StateContent stateContent = (StateContent) p_content;
            lastState = currentState;
            currentState = stateContent.getState();


            if (lastState != null)
            {
                if (lastState.getCurrentPlayerId() != currentState.getCurrentPlayerId())
                {
                    StatePlayer[] players = currentState.getPlayers();
                    int playerId = lastState.getCurrentPlayerId();

                    if (playerId >= 0)
                    {
                        if (players[playerId] != null)
                        {
                            if (players[playerId].isDead()) {
                                setImageDead();
                                try {
                                    Thread.sleep(2000);
                                    setImageNormal();
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                setImageLive();
                                try {
                                    Thread.sleep(2000);
                                    setImageNormal();
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                    else
                    {
                        logger.debug("playerId = NOT_PLAYING");
                    }
                }
                else
                {
                    logger.debug("spun!");
                }
            }
            
            if (currentState.getCurrentPlayerId() == myId) {
                gui.toggleButtonActivity(true);
            } else {
                gui.toggleButtonActivity(false);
            }

            logger.debug("HEELLLOO!?");
            gui.setLabelScore(String.valueOf(currentState.getPointsForShooting()));
        }
    }

    public void Shoot() {
        send(new Content(Content.PEWPEW_SHOOT));
    }

    public void Spin() {
        send(new Content(Content.PEWPEW_SPIN));
    }

    public void Que() {
        send(new Content(Content.PEWPEW_REPLAY));
    }

    public void Exit() {
        send(new Content(Content.PEWPEW_EXIT));
    }
}
