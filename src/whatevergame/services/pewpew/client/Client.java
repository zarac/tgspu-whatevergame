package whatevergame.services.pewpew.client;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import whatevergame.services.ClientService;

import whatevergame.services.pewpew.Content;
import whatevergame.services.pewpew.ContentServer;

/**
 * The testing client. It responds to a received message.
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
public class Client extends ClientService
{
    private ImageIcon[] arrLive, arrDead, arrNormal;
    private Random rand;
    private int randomInt = 0;
    private ArrayList<ImageIcon> myArray;
    private PewPewGui gui;
    /**
     * {@inheritDoc}
     * @see ClientService#Client(int,Client)
     */
    public Client(int id, whatevergame.client.Client client)
    {
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
    public void enable()
    {
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
            pathLive = "src/whatevergame/images/pewPewLive" + i + ".jpg";
            arrLive[i] = resizeImage(pathLive);
        }
        for (int i = 0; i < arrDead.length; i++) {
            pathDead = "src/whatevergame/images/pewPewDead" + i + ".jpg";
//            arrDead[i] = new ImageIcon(pathDead);
            arrDead[i] = resizeImage(pathDead);
        }
        for (int i = 0; i < arrNormal.length; i++) {
            pathNormal = "src/whatevergame/images/pewPewNormal" + i + ".jpg";
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
    public void receive(whatevergame.services.Content p_content)
    { 
        if (p_content instanceof Content)
        {
            Content content = (Content)p_content;
        }
        else if (p_content instanceof ContentServer)
        {
            ContentServer inContent = (ContentServer)p_content;
            //        gui.toggleButtonActivity(false);
            System.out.println(inContent.toString());

            if (inContent.isWaitForPlayers()==false) {
                if(inContent.isTurn()){
                    gui.toggleButtonActivity(true);
                }
            }

            if (inContent.isDead()){
                setImageDead();
            }

            if (inContent.isAllDead()) {
                gui.toggetButtonActivityGameOver();
            }

            logger.debug("Received content\n    [" + (whatevergame.services.pewpew.ContentServer) inContent + "]");
        }
    }
 
    public void Shoot() {
        super.send(new Content(Content.PEWPEW_SHOOT));
    }
 
    public void Spinn() {
        super.send(new Content(Content.PEWPEW_SPINN));
    }
 
    public void Que() {
        super.send(new Content(Content.PEWPEW_QUE));
    }
 
    public void Exit() {
        super.send(new Content(Content.PEWPEW_EXIT));
    }

    
}
