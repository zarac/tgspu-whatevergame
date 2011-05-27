package whatevergame.server;

import java.net.Socket;

import whatevergame.communication.Connection;

import whatevergame.server.User;

import whatevergame.services.ServerService;
import whatevergame.services.Service;
import whatevergame.services.ServiceProvider;

/**
 * Used to keep track of clients connected to the server.
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
public class Client extends Connection
{
    protected User user;

    /**
     * Creates an instance of Client.
     * 
     * @param connection The connection to the client.
     */
    public Client(ServiceProvider services, Socket socket, int sessionId)
    {
        //src/whatevergame/server/Client.java|31 col 15 error| Cannot cast from ServiceProvider<ServerService> to ServiceProvider<Service>
        //super((ServiceProvider<Service>)services, socket, sessionId);
        super(services, socket, sessionId);
        connect();
    }

    /**
     * Gets the user for this instance.
     *
     * @return The user.
     */
    public User getUser()
    {
        return this.user;
    }

    /**
     * Sets the user for this instance.
     *
     * @param user The user.
     */
    public void setUser(User user)
    {
        this.user = user;
    }
}
