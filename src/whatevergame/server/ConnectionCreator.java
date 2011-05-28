package whatevergame.server;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import logging.Logger;

import whatevergame.communication.Connection;

/**
 * Waits connecting clients.
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
public class ConnectionCreator extends Thread
{
    protected Server server;
    protected ServerSocket serverSocket;
    protected boolean acceptingConnections;
    protected int nextSessionId;
    protected DisconnectionHandler disconnectionHandler;

    /**
     * A logger, it's handy to have.
     */
    protected Logger logger;

    /**
     * Creates an instance of ConnectionCreator.
     * 
     * @param server The server instance to add connections to.
     */
    public ConnectionCreator(Server server)
    {
        logger = new Logger(this);

        this.server = server;
        nextSessionId = 0;
        disconnectionHandler = new DisconnectionHandler();
    }

    /**
     * Tries to create the socket used for accepting connections.
     * 
     * @return Whether successful or not.
     */
    protected boolean init()
    {
        try
        {
            logger.info("Creating ServerSocket.");
            serverSocket = new ServerSocket(server.getPort());
            return true;
        }
        catch (IOException e)
        {
            logger.error("Could not create socket (" + e.getMessage() + ").");
        }

        acceptingConnections = false;
        return false;
    }

    /**
     * {@inheritDoc}
     * @see Runnable#run()
     */
    public void run()
    {
        acceptingConnections = true;
        while (acceptingConnections)
        {
            Client client = new Client(server.getServices(), disconnectionHandler);

            logger.info("waiting for incoming connection...");
            try
            {
                Socket socket = serverSocket.accept();
                logger.info("accepted socket, creating client");
                client.connect(socket);
                // TODO : better session id
                client.setSessionId(nextSessionId++);
                server.addClient(client);
            }
            catch (IOException e)
            {
                logger.error("Could not accept connection (" + e.getMessage() + ").");
            }
        }
    }

    /**
     * Determines if this instance is acceptingConnections.
     *
     * @return The acceptingConnections.
     */
    public boolean isAcceptingConnections()
    {
        return acceptingConnections;
    }

    /**
     * Sets whether or not this instance is acceptingConnections.
     *
     * @param acceptingConnections The acceptingConnections.
     */
    public void setAcceptingConnections(boolean acceptingConnections)
    {
        this.acceptingConnections = acceptingConnections;
    }

    protected class DisconnectionHandler implements whatevergame.communication.DisconnectionHandler
    {
        public void disconnected(Connection connection)
        {
            Client client = (Client)connection;

            logger.info("client disconnected: " + client);
            server.removeClient(client);
        }
    }
}
