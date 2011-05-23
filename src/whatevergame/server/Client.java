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
    /**
     * Creates an instance of Client.
     * 
     * @param connection The connection to the client.
     */
    public Client(Service[] services, Socket socket, int sessionId)
    {
        super(services, socket, sessionId);
    }
}
