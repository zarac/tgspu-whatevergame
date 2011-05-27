package whatevergame.services.lobby.client;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Gui extends JFrame implements WindowListener
{
    protected Client client;

    protected whatevergame.services.pewpew.client.Client pewPew;
    protected whatevergame.services.fivepad.client.Client fivePad;

    protected Game game;
    protected Chat chat;
    protected HighScore highScore;

    public Gui(Client client)
    {
        this.client = client;

        setTitle("Whatever Game");
        setVisible(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(this);

        game = new Game();
        chat = new Chat();
        highScore = new HighScore();
    }

    public void playPewPew()
    {
        game.selectGame(client.getPewPew().getGui());
    }

    public void playFivePad()
    {
        game.selectGame(client.getFivePad().getGui());
    }

    public void selectGame()
    {
        game.selectGame();
    }

    protected void init()
    {
        // TODO : why is frame empty when using removeAll(): ?
        // we need to empty frame when we init.. how to?
        //removeAll();

        setLayout(new GridLayout(1,2));

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(dimension.width/4, dimension.height/4, dimension.width/2, dimension.height/2);

        game.init();
        add(game);

        JPanel right = new JPanel();
        right.setLayout(new GridLayout(2,1));

        chat.init();
        right.add(chat);

        highScore.init();
        right.add(highScore);

        right.revalidate();
        add(right);
    }

    protected void init2()
    {
        setLayout(null);
        Insets insets = getContentPane().getInsets();

        removeAll();

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(dimension.width/4, dimension.height/4, dimension.width/2, dimension.height/2);

        game.setBounds(0 + insets.left, 0, dimension.width/2 + insets.top, dimension.height);
        add(game);
        game.revalidate();

        chat.setBounds(dimension.width/2 + insets.left, 0 + insets.top, dimension.width, dimension.height/2);
        add(chat);
        chat.revalidate();

        highScore.setBounds(dimension.width/2 + insets.left, dimension.height/2 + insets.top, dimension.width, dimension.height);
        add(highScore);
        highScore.revalidate();
    }

    /**
     * {@inheritDoc}
     * @see WindowListener#windowOpened(WindowEvent)
     */
    public void windowOpened(WindowEvent e)
    {
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
    }

    /**
     * {@inheritDoc}
     * @see WindowListener#windowIconified(WindowEvent)
     */
    public void windowIconified(WindowEvent e)
    {
    }

    /**
     * {@inheritDoc}
     * @see WindowListener#windowDeiconified(WindowEvent)
     */
    public void windowDeiconified(WindowEvent e)
    {
    }

    /**
     * {@inheritDoc}
     * @see WindowListener#windowActivated(WindowEvent)
     */
    public void windowActivated(WindowEvent e)
    {
    }

    /**
     * {@inheritDoc}
     * @see WindowListener#windowDeactivated(WindowEvent)
     */
    public void windowDeactivated(WindowEvent e)
    {
    }

    protected class Game extends JPanel
    {
        protected GameSelector gameSelector;

        protected Game()
        {
            gameSelector = new GameSelector();
        }

        protected void init()
        {
            selectGame();
            revalidate();
        }

        public void selectGame(JPanel panel)
        {
            removeAll();
            add(panel);
            revalidate();
        }

        public void selectGame()
        {
            selectGame(gameSelector);
        }

        protected class GameSelector extends JPanel
        {
            protected GameSelector()
            {
                setLayout(new GridLayout(2,1));
                add(new SelectPewPew());
                add(new SelectFivePad());
            }

            protected class SelectFivePad extends JButton implements ActionListener
            {
                protected SelectFivePad()
                {
                    setText("FIVE PAD");
                    addActionListener(this);
                }

                /**
                 * {@inheritDoc}
                 * @see ActionListener#actionPerformed(ActionEvent)
                 */
                public void actionPerformed(ActionEvent e)
                {
                    playFivePad();
                }
            }

            protected class SelectPewPew extends JButton implements ActionListener
            {
                protected SelectPewPew()
                {
                    setText("PEW PEW!");
                    addActionListener(this);
                }

                /**
                 * {@inheritDoc}
                 * @see ActionListener#actionPerformed(ActionEvent)
                 */
                public void actionPerformed(ActionEvent e)
                {
                    playPewPew();
                }
            }
        }

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
}
