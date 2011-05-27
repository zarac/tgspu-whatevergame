package whatevergame.services.lobby.client;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import whatevergame.services.Service;

@SuppressWarnings("serial")
public class Gui extends JFrame implements WindowListener
{
    protected Client client;

    protected JPanel mainPanel;

    protected GameSelector gameSelector;
    protected JPanel gameBox;
    protected Chat chat;
    protected HighScore highScore;
    protected BackToGameSelect backToGameSelect;

    protected Dimension dimension;

    public Gui(Client client)
    {
        this.client = client;

        setTitle("Whatever Game");
        setVisible(false);
        setLayout(new GridLayout(1,1));
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
        dimension = Toolkit.getDefaultToolkit().getScreenSize();

        mainPanel = new JPanel();
        gameSelector = new GameSelector();
        gameBox = new JPanel();
        chat = new Chat();
        highScore = new HighScore();
        backToGameSelect = new BackToGameSelect();

        add(mainPanel);
    }

    public void playPewPew()
    {
        gameBox.removeAll();
        client.getService(Service.PEWPEW).enable();
        client.getService(Service.PEWPEW).getGui().revalidate();
        gameBox.add(client.getService(Service.PEWPEW).getGui());
        gameBox.revalidate();
    }

    public void playFivePad()
    {
        gameBox.removeAll();
        client.getService(Service.FIVEPAD).enable();
        client.getService(Service.FIVEPAD).getGui().revalidate();
        gameBox.add(client.getService(Service.FIVEPAD).getGui());
        gameBox.revalidate();
    }

    public void selectGame()
    {
        client.getService(Service.PEWPEW).disable();
        client.getService(Service.FIVEPAD).disable();
        gameBox.removeAll();
        gameSelector.revalidate();
        gameBox.add(gameSelector);
        gameBox.revalidate();
    }

    protected void init()
    {
        setBounds(dimension.width/4, dimension.height/4, dimension.width/2, dimension.height/2);

        mainPanel.removeAll();

        mainPanel.setLayout(new GridLayout(1,2));

        selectGame();
        mainPanel.add(gameBox);

        JPanel right = new JPanel();
        right.setLayout(new GridLayout(2,1));

        chat.init();
        right.add(chat);

        highScore.init();
        right.add(highScore);

        right.revalidate();
        mainPanel.add(right);

        mainPanel.revalidate();
    }

    /**
     * {@inheritDoc}
     * @see WindowListener#windowOpened(WindowEvent)
     */
    public void windowOpened(WindowEvent e)
    {
        // pass
    }

    /**
     * {@inheritDoc}
     * @see WindowListener#windowClosing(WindowEvent)
     */
    public void windowClosing(WindowEvent e)
    {
        client.disable();
    }

    /**
     * {@inheritDoc}
     * @see WindowListener#windowClosed(WindowEvent)
     */
    public void windowClosed(WindowEvent e)
    {
        // pass
    }

    /**
     * {@inheritDoc}
     * @see WindowListener#windowIconified(WindowEvent)
     */
    public void windowIconified(WindowEvent e)
    {
        // pass
    }

    /**
     * {@inheritDoc}
     * @see WindowListener#windowDeiconified(WindowEvent)
     */
    public void windowDeiconified(WindowEvent e)
    {
        // pass
    }

    /**
     * {@inheritDoc}
     * @see WindowListener#windowActivated(WindowEvent)
     */
    public void windowActivated(WindowEvent e)
    {
        // pass
    }

    /**
     * {@inheritDoc}
     * @see WindowListener#windowDeactivated(WindowEvent)
     */
    public void windowDeactivated(WindowEvent e)
    {
        // pass
    }

    /**
     * Gets the backToGameSelect for this instance.
     *
     * @return The backToGameSelect.
     */
    public BackToGameSelect getBackToGameSelect()
    {
        return this.backToGameSelect;
    }

    protected class Chat extends JPanel
    {
        protected void init()
        {
            setLayout(new BorderLayout());
            add(client.getChat().getGui());
            revalidate();
        }
    }

    protected class HighScore extends JPanel
    {
        protected void init()
        {
            setLayout(new BorderLayout());
            add(client.getScore().getGui());
            revalidate();
        }
    }

    // TODO : use something else than JButton
    protected class PlayFivePad extends JButton implements MouseListener
    {
        protected final static String iconPath = "data/images/logo5pad.jpg";

        protected PlayFivePad()
        {
            int width = dimension.width/4;
            int height = dimension.height/4;
            //setBounds(0, height/2, width, height);
            setPreferredSize(new Dimension(width, height));

            addMouseListener(this);
            // TODO : Shouldn't be hard coded
            //setIconImage(getScaledImage(iconPath, width, height));
            setIcon(new ImageIcon(getScaledImage(iconPath, width, height)));
            add(new JLabel("Five Pad"));
        }

        /**
         * {@inheritDoc}
         * @see MouseListener#mouseClicked(MouseEvent)
         */
        public void mouseClicked(MouseEvent e)
        {
            // pass
        }

        /**
         * {@inheritDoc}
         * @see MouseListener#mousePressed(MouseEvent)
         */
        public void mousePressed(MouseEvent e)
        {
            // pass
        }

        /**
         * {@inheritDoc}
         * @see MouseListener#mouseReleased(MouseEvent)
         */
        public void mouseReleased(MouseEvent e)
        {
            playFivePad();
        }

        /**
         * {@inheritDoc}
         * @see MouseListener#mouseEntered(MouseEvent)
         */
        public void mouseEntered(MouseEvent e)
        {
            // pass
        }

        /**
         * {@inheritDoc}
         * @see MouseListener#mouseExited(MouseEvent)
         */
        public void mouseExited(MouseEvent e)
        {
            // pass
        }
    }

    protected class PlayPewPew extends JButton implements MouseListener
    {
        protected final static String iconPath = "data/images/logoPewWep.jpg";

        protected PlayPewPew()
        {
            int width = dimension.width/4;
            int height = dimension.height/4;
            new logging.Logger(this).debug("width=" + width + ", height=" + height);
            setPreferredSize(new Dimension(width, height));
            //setBounds(0, 0, width, height);

            //setText("PEW PEW!");
            addMouseListener(this);
            // TODO : Shouldn't be hard coded
            setIcon(new ImageIcon(getScaledImage(iconPath, width, height)));
            //setIconImage(getScaledImage(iconPath, width, height));
            add(new JLabel("Pew Wep"));
        }

        /**
         * {@inheritDoc}
         * @see ActionListener#actionPerformed(ActionEvent)
         */
        public void actionPerformed(ActionEvent e)
        {
            // pass
        }

        /**
         * {@inheritDoc}
         * @see MouseListener#mouseClicked(MouseEvent)
         */
        public void mouseClicked(MouseEvent e)
        {
            // pass
        }

        /**
         * {@inheritDoc}
         * @see MouseListener#mousePressed(MouseEvent)
         */
        public void mousePressed(MouseEvent e)
        {
            // pass
        }

        /**
         * {@inheritDoc}
         * @see MouseListener#mouseReleased(MouseEvent)
         */
        public void mouseReleased(MouseEvent e)
        {
            playPewPew();
        }

        /**
         * {@inheritDoc}
         * @see MouseListener#mouseEntered(MouseEvent)
         */
        public void mouseEntered(MouseEvent e)
        {
            // pass
        }

        /**
         * {@inheritDoc}
         * @see MouseListener#mouseExited(MouseEvent)
         */
        public void mouseExited(MouseEvent e)
        {
            // pass
        }
    }

    protected class GameSelector extends JPanel
    {
        protected GameSelector()
        {
            setLayout(new GridLayout(2,1));
            add(new PlayPewPew());
            add(new PlayFivePad());
        }
    }

    protected class BackToGameSelect extends JButton implements ActionListener
    {
        public BackToGameSelect()
        {
            setText("Back to game selection");
            addActionListener(this);
        }

        /**
         * {@inheritDoc}
         * @see ActionListener#actionPerformed(ActionEvent)
         */
        public void actionPerformed(ActionEvent e)
        {
            selectGame();
        }
    }

    protected Image getScaledImage(String p_imagePath, int width, int height)
    {
        // TODO : check if image exists
        Image image = new ImageIcon(p_imagePath).getImage();

        image = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);

        return image;
    }
}
