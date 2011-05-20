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
            logger.info("Waiting for incoming connection.");
            try
            {
                Socket socket = serverSocket.accept();
                logger.info("Accepted socket.");
                Connection connection = new Connection(server.getServices(), socket, nextSessionId++);
                //logger.info("Created connection '" + connection + "'.");
                Client client = new Client(connection);
                //logger.info("Created client '" + client + "'.");
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
}
