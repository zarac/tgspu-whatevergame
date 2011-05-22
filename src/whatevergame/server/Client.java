package whatevergame.server;

import java.net.Socket;

import whatevergame.communication.Connection;

import whatevergame.services.Service;

/**
 * Used to keep track of clients connected to the server.
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
public class Client extends Connection
{
    //protected Connection connection;

    /**
     * Creates an instance of Client.
     * 
     * @param connection The connection to the client.
     */
    //public Client(Connection connection)
    public Client(Service[] services, Socket socket, int sessionId)
    {
        super(services, socket, sessionId);
        //this.connection = connection;
    }

    /**
     * Get the clients IP address.
     * 
     * @return The client's IP address.
     */
    //public String getIpAddress()
    //{
        //return connection.getIpAddress();
    //}

    /**
     * Forwards a package to be sent. It uses a Connection for this.
     * 
     * @param p_package The package to send.
     */
    //public void send(Package p_package)
    //{
        //connection.send(p_package);
    //}
}
