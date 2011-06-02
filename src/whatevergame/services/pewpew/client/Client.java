package whatevergame.services.pewpew.client;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import whatevergame.services.ClientService;
import whatevergame.services.Service;

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
    private State currentState, previousState;
    protected final static int NOT_PLAYING = -1;
    private String pathGameOver = "data/images/pewPewGameOver.jpg";

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
        currentState = null;
        previousState = null;
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
        gui.revalidate();
    }

    // Från CPP
    public void setImageNormal() {
        randomInt = rand.nextInt(arrNormal.length);
        gui.setImageToLabel(arrNormal[randomInt]);
        gui.revalidate();
    }

    // Från CPPP
    public void setImageDead() {
        randomInt = rand.nextInt(arrDead.length);
        gui.setImageToLabel(arrDead[randomInt]);
        gui.revalidate();
    }

    public void setImageGameOver() {
        gui.setImageToLabel(resizeImage(pathGameOver));
        gui.revalidate();
    }

    // Från CPP
    public void setGuiPewPew(PewPewGui p_pewPew) {
        this.gui = p_pewPew;
    }

    @Override
    public void receive(whatevergame.services.Content p_content) {


        if (p_content instanceof Content) {
            Content content = (Content) p_content;

            if (content.getCommand() == Content.PLAYER_ID) {
                myId = content.getPlayerId();
            }
            if (content.getCommand() == Content.GAME_OVER) {
                gui.toggleButtonGameOver();
            }
        } else if (p_content instanceof StateContent) {
            StateContent stateContent = (StateContent) p_content;
            previousState = currentState;
            currentState = stateContent.getState();

            logger.debug("stateContent currentState.getState()" + currentState.getState());

            updateLabelPlayer();

            // OM EJ PLAYING
            if (currentState.getState() == State.NOT_PLAYING) {
                if (currentState.getPlayer(myId).isReady()) {
                    setImageNormal();
                    gui.toggleButtonNotStarted();
                } else {
                    gui.toggleButtonGameOver();
                }


                updateLabelPlayer();
                gui.setLabelScore("Points per shot: " + currentState.getPointsForShooting());

            } else if (currentState.getState() == State.PLAYING) {
                if (currentState.getCurrentPlayerId() == myId) {
                    gui.toggleButtonPlayingCurrentPlayer();
                } else {
                    gui.toggleButtonPlayingNotCurrentPlayer();
                }

                if (previousState != null) {
                    // Somebody shot, next player
                    if (previousState.getCurrentPlayerId() != currentState.getCurrentPlayerId()) {
                    StatePlayer[] players = currentState.getPlayers();

                        int lastPlayerId = previousState.getCurrentPlayerId();

                        // Spelet pågår fortfarande
                        if (lastPlayerId >= 0) {
                            if (players[lastPlayerId] != null) {
                                if (players[lastPlayerId].isDead()) {
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
                    } else {
                        logger.debug("spun");
                    }
                } else {
                    setImageNormal();
                }

                gui.setLabelScore("Points for shooting: " + String.valueOf(currentState.getPointsForShooting()));

            } else if (currentState.getState() == State.GAME_OVER) {

                setImageDead();
                try {
                    Thread.sleep(2000);
                    setImageGameOver();
                } catch (InterruptedException ex) {
                    logger.error("State.GAME_OVER = InterruptedException: " + ex);
                }
                gui.toggleButtonGameOver();
            }
        }
    }
            
    public void disable() {
        send(new Content(Content.PEWPEW_EXIT));
        logger.debug("in disable()");
        currentState = null;
        previousState = null;
    }

    public void updateLabelPlayer() {
        String labelPlayersString = "";
        for (StatePlayer player : currentState.getPlayers()) {
            if (player != null) {
                if (player.isDead())
                    labelPlayersString += "-";
                labelPlayersString += player.getName() + " ";
            }
        }
        gui.setLabelPlayers(labelPlayersString);
    }

    public void shoot() {
        send(new Content(Content.PEWPEW_SHOOT));
    }

    public void spin() {
        send(new Content(Content.PEWPEW_SPIN));
    }

    public void replay() {
        send(new Content(Content.PEWPEW_REPLAY));
        currentState = null;
        previousState = null;
    }

    public void exit() {
        send(new Content(Content.PEWPEW_EXIT));
        disable();
        whatevergame.services.lobby.client.Client lobby = (whatevergame.services.lobby.client.Client) client.getService(Service.LOBBY);
        lobby.selectGame();

    }
}
