package whatevergame.services.lobby.client;

import java.awt.BorderLayout;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Gui extends JFrame implements WindowListener
{
    protected Client client;

    public Gui(Client client)
    {
        this.client = client;

        setTitle("Whatever Game");
        setVisible(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        setBounds(100, 100, 800, 600);
        addWindowListener(this);

        add(new JLabel("HELLOOO =D"));
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
}
