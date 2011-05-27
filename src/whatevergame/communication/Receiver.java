package whatevergame.communication;

import java.io.InputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import java.lang.ClassNotFoundException;

import logging.Logger;

import whatevergame.services.Package;
import whatevergame.services.Service;
import whatevergame.services.ServiceProvider;

/**
 * Waits for incoming packages on a stream and directs them to belonging service.
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
public class Receiver extends Thread
{
    /**
     * The connection we're receiving for.
     */
    protected Connection connection;

    /**
     * The input stream. Incoming data.
     */
    protected ObjectInputStream ois;

    /**
     * The status.
     */
    protected boolean active;

    /**
     * The services, used to route package.
     */
    protected ServiceProvider services;

    /**
     * A logger, it's handy to have.
     */
    protected Logger logger;

    /**
     * Creates instance of Receiver.
     * 
     * @param connection Connection used to create receiver.
     */
    public Receiver(Connection connection)
    {
        super("ReceiverForSessionId:" + connection.getSessionId());
        logger = new Logger(this);

        this.connection = connection;
        this.services = connection.getServices();
    }

    /**
     * Initializes the receiver.
     * 
     * @throws IOException
     */
    public void init() throws IOException
    {
        logger.debug("Trying to set up receiver...");
        InputStream stream = connection.getSocket().getInputStream();
        logger.debug("Got InputStream");
        ois = new ObjectInputStream(stream);
        logger.debug("Created ObjectInputStream");

        logger.debug("services=" + services);
        logger.info("starting receiver");
        start();
        logger.debug("started receiver");
    }

    /**
     * {@inheritDoc}
     * @see Runnable#run()
     */
    public void run()
    {
        active = true;
        while (active)
        {
            logger.debug("Waiting for next package...");
            try
            {
                Package _package = (Package)ois.readObject();
                logger.debug("received package\n    [" + _package + "]");
                logger.debug("routing to service: " + services.get(_package.getServiceId()));
                // TODO : Should add to buffer!
                services.get(_package.getServiceId()).receivePackage(connection, _package);
            }
            catch (IOException e)
            {
                logger.error("Could not read object from object input stream (" + e.getMessage() + ").");
                logger.error("Deactivating!");
                active = false;
            }
            catch (ClassNotFoundException e)
            {
                logger.error("Class not found (" + e.getMessage() + ").");
                logger.error("Deactivating!");
                active = false;
            }
        }
    }
}
