package whatevergame;

import java.util.LinkedList;

import javax.swing.JFrame;

import whatevergame.communication.Communicator;
import whatevergame.communication.Reciever;
import whatevergame.communication.Sender;

public class Server
{
    protected final static int PORT = 3000;

    protected Communicator communicator;
    protected Service[] services;
    protected LinkedList<Session> sessions;
    protected Gui gui;

    public Server()
    {
        gui = new Gui(this);

        services = new Service[Service.COUNT];
        whatevergame.services.login.Server loginServer = new
            whatevergame.services.login.Server(Service.LOGIN, new
                    Communicator(new Sender(), new Reciever()));
    }
    
    public static void main(String[] args)
    {
        new Server();
    }

    @SuppressWarnings("serial")
    public class Gui extends JFrame
    {
        public Gui(Server server)
        {
        }
    }
}
