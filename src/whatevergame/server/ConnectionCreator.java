package whatevergame.server;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import logging.Logger;

import whatevergame.communication.Connection;

import whatevergame.services.Service;

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
            serverSocket = new ServerSocket(server.getPort());
            acceptingConnections = true;
            return true;
        }
        catch (IOException e)
        {
            logger.error("ERROR: Could not create socket (" + e.getMessage() + ").");
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
        while (acceptingConnections)
        {
            logger.info("ConnectionCreator(): Waiting for incoming connection.");
            try
            {
                Socket socket = serverSocket.accept();
                logger.info("ConnectionCreator(): Accepted socket.");
                Connection connection = new Connection(server.getServices(), socket, nextSessionId++);
                logger.info("ConnectionCreator(): Created connection.");
                Client client = new Client(connection);
                server.addClient(client);
                logger.info("ConnectionCreator(): Added client.");
                // test
                server.services[Service.TEST].addClient(client);
            }
            catch (IOException e)
            {
                logger.error("ERROR: Could not accept connection (" + e.getMessage() + ").");
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
