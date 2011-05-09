package whatevergame;

import javax.swing.JFrame;

public class Server
{
    public Server()
    {
    }
    
    public static void main(String[] args)
    {
        Server server = new Server();
        server.new GUI(server);
        //new Server.GUI(new Server());
    }

    @SuppressWarnings("serial")
    public class GUI extends JFrame
    {
        public GUI(Server server)
        {
        }
    }
}
