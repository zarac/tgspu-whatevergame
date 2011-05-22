package whatevergame.communication;

import java.io.InputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;

import logging.Logger;

import whatevergame.services.Package;
import whatevergame.services.Service;

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
    protected Service[] services;

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
    public Connection(Service[] services, Socket socket, int sessionId)
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
    public void init(Service[] services, Socket socket, int sessionId)
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
            logger.debug("Created Sender");
            //InputStream stream = socket.getInputStream();
            //logger.debug("Got InputStream");
            // TODO : ? time out waiting for inputStream
            //ObjectInputStream ois = new ObjectInputStream(stream);
            //logger.debug("Created ObjectInputStream");
            //receiver = new Receiver(services, ois);
            receiver = new Receiver(this);
            receiver.init();
            logger.debug("Created Receiver");
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
     * Gets the services for this instance.
     *
     * @return The services.
     */
    public Service[] getServices()
    {
        return this.services;
    }

    /**
     * Gets the services for this instance.
     *
     * @param index The index to get.
     * @return The services.
     */
    public Service getServices(int index)
    {
        return this.services[index];
    }
}
