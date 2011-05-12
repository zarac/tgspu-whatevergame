package whatevergame.services.login;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import whatevergame.communication.Communicator;

import whatevergame.Package;
import whatevergame.Service;

public class Server extends Service
{
    protected Gui gui;

    /**
     * @see Service#Service(int,Communicator)
     */
    public Server(int serviceId, Communicator communicator)
    {
        super(serviceId, communicator);
        gui = new Gui();
    }

    /**
     * {@inheritDoc}
     * @see Service#sendPackage(Package)
     */
    public void sendPackage(Package p_package)
    {
    }

    /**
     * {@inheritDoc}
     * @see Service#recievePackage(Package)
     */
    public void recievePackage(Package p_package)
    {
    }

    public JPanel getGui()
    {
        return gui;
    }

    @SuppressWarnings("serial")
    protected class Gui extends JPanel
    {
        public Gui()
        {
            setBackground(Color.BLUE);
            setLayout(new FlowLayout());
            setVisible(true);
            add(new JLabel("Hello World, i'm a server service!"));
            revalidate();
        }
    }
}
