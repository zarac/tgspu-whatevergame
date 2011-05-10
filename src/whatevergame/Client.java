package whatevergame;

import javax.swing.JFrame;

import whatevergame.communication.Communicator;
import whatevergame.communication.Connection;
import whatevergame.communication.Sender;
import whatevergame.communication.Reciever;

public class Client
{
    // Server settings
    // TODO : Should not be hard-coded
    protected final static String address = "10.2.2.121";
    protected final static int port = 3000;

    protected Communicator communicator;
    protected Service[] services;
    // The connection to the server.
    protected Session session;

    protected Gui gui;

    public Client()
    {
        // TODO : For testing...
        new Server();
        System.out.println("Server up and running!");

        // Communicator
        //Sender sender = new Sender();
        //sender.start();
        //Reciever reciever = new Reciever();
        //reciever.start();
        //communicator = new Communicator(sender, reciever);

        // Services
        // TODO : ? Should not be hard-coded?  The login service is required,
        // hence we're hard-coding all at the moment.
        services = new Service[Service.COUNT];

        // Session
        Connection connection = new Connection(address, port);
        if (connection.connect())
        {
            session = new Session(connection, -1);

            // TODO : TESTING...
            // LogIn
            whatevergame.services.login.Client loginClient = new
                whatevergame.services.login.Client(Service.LOGIN, new
                        Communicator(new Sender(), new Reciever()), session);
            gui = new Gui();
            services[Service.LOGIN] = loginClient;
            services[Service.LOGIN].start();
            gui.add(loginClient.getGui());
        }
        else
        {
            System.out.println("ERROR: Cannot connect to server.");
        }
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
