package whatevergame;

import javax.swing.JFrame;

import whatevergame.communication.Communicator;
import whatevergame.communication.Connection;
import whatevergame.communication.Sender;
import whatevergame.communication.Reciever;

public class Client
{
    private final static String address = "10.2.2.121";
    private final static int port = 3000;

    protected Communicator communicator;
    protected Service[] services;
    // The connection to the server.
    protected Session session;

    protected Gui gui;

    public Client()
    {
        // Communicator
        Sender sender = new Sender();
        //sender.start();
        Reciever reciever = new Reciever();
        //reciever.start();
        communicator = new Communicator(sender, reciever);

        // Services
        // TODO : ? Should not be hard-coded?  The login service is required,
        // hence we're hard-coding all at the moment.
        services = new Service[4];

        // Session
        Connection connection = new Connection(address, port);
        session = new Session(connection, -1);

        // TODO : TESTING...
        whatevergame.services.login.Client loginClient = new whatevergame.services.login.Client();
        gui = new Gui();
        services[0] = loginClient;
        services[0].start();
        gui.add(loginClient.getGui());
    }

    public static void main(String[] args)
    {
        new Client();
    }

    @SuppressWarnings("serial")
    public class Gui extends JFrame
    {
        public Gui()
        {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setBounds(100, 100, 640, 480);
            setVisible(true);
        }
    }
}
