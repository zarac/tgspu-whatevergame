package whatevergame.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;

import java.net.Socket;

import logging.Logger;

import whatevergame.services.Package;
import whatevergame.services.Service;
import whatevergame.services.ServiceProvider;

/**
 * Used for communicating of the internets. ; )
 * TODO : Interface to take care of redirection instead of Service[]. Should not
 * be dependant on something outside this package.
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
public class Connection
{
    protected Sender sender;
    protected Receiver receiver;
    protected Socket socket;
    protected int sessionId;
    protected ServiceProvider services;

    /**
     * A logger, it's handy to have.
     */
    protected Logger logger;

    /**
     * Constructs a new instance.
     */
    public Connection()
    {
        logger = new Logger(this);
    }

    /**
     * Constructs a new instance of Connection and initializes it.
     * 
     * @param services Services needed for directing incoming packages.
     * @param socket The socket used for communication.
     * @param sessionId A session identification number.
     */
    public Connection(ServiceProvider services, Socket socket, int sessionId)
    {
        logger = new Logger(this);
        init(services, socket, sessionId);
    }

    /**
     * Initializes the Connection.
     * 
     * @param services Services needed for directing incoming packages.
     * @param socket The socket used for communication.
     * @param sessionId A session identification number.
     */
    public void init(ServiceProvider services, Socket socket, int sessionId)
    {
        this.services = services;
        this.socket = socket;
        this.sessionId = sessionId;
    }

    /**
     * Tries to connect and create a sender and receiver.
     * 
     * @return If successful or not.
     */
    public boolean connect()
    {
        // TODO : clean up!
        logger.debug("Trying to set up connection...");
        try
        {
            sender = new Sender(new ObjectOutputStream(socket.getOutputStream()));
            logger.info("Created Sender");
            // TODO : ? time out waiting for inputStream
            receiver = new Receiver(this);
            receiver.init();
            logger.info("Created Receiver");
            return true;
        }
        catch (IOException e)
        {
            logger.error("Could not get output/input stream (" + e.getMessage() + ").");
            return false;
        }
    }

    /**
     * Queues a package in the sender.
     * 
     * @param p_package The package to enqueue.
     */
    public void send(Package p_package)
    {
        logger.debug("sender = " + sender);
        sender.enqueue(p_package);
    }

    /**
     * Get the IP used for connection.
     * 
     * @return The IP address used for connection.
     */
    public String getIpAddress()
    {
        return socket.getInetAddress().toString();
    }

    /**
     * Gets the socket for this instance.
     *
     * @return The socket.
     */
    public Socket getSocket()
    {
        return this.socket;
    }

    /**
     * Gets the sessionId for this instance.
     *
     * @return The sessionId.
     */
    public int getSessionId()
    {
        return this.sessionId;
    }

    /**
     * Gets the service provider for this instance.
     *
     * @return The services provider.
     */
    public ServiceProvider getServices()
    {
        return this.services;
    }

    /**
     * {@inheritDoc}
     * @see Object#toString()
     */
    public String toString()
    {
        return "Connection: IP-Address='" + getIpAddress() + "'";
    }
}
