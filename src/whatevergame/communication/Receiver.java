package whatevergame.communication;

import java.io.InputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import java.lang.ClassNotFoundException;

import logging.Logger;

import whatevergame.services.Package;
import whatevergame.services.Service;

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
    protected Service[] services;

    /**
     * A logger, it's handy to have.
     */
    protected Logger logger;

    /**
     * Creates instance of Receiver.
     * 
     * @param services Needed so we know where to direct the package.
     * @param ois Stream used for communicating.
     */
    //public Receiver(Service[] services, ObjectInputStream ois)
    //public Receiver(Connection connection, ObjectInputStream ois)
    public Receiver(Connection connection)
    {
        logger = new Logger(this);

        this.connection = connection;
        this.services = connection.getServices();
    }

    public void init() throws IOException
    {
        logger.debug("Trying to set up receiver...");
        InputStream stream = connection.getSocket().getInputStream();
        logger.debug("Got InputStream");
        ois = new ObjectInputStream(stream);
        logger.debug("Created ObjectInputStream");

        logger.info("services=" + services);
        logger.debug("Starting receiver...");
        start();
        logger.debug("Started receiver");
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

                logger.debug("Received package\n    [" + _package + "]");
                logger.debug("Sending to service\n    [" + _package.getServiceId() + "]");
                dumpServices();
                logger.debug("service=" + services[_package.getServiceId()]);
                // TODO : Should add to buffer!
                //services[_package.getServiceId()].receivePackage(_package);
                services[_package.getServiceId()].receivePackage(connection, _package);
                logger.debug("HEEELLLOO?");
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

    public void dumpServices()
    {
        logger.debug("Services:");
        for (Service service : services)
        {
            if (service != null)
                logger.debug(service.toString());
            else
                logger.debug("NULL");
        }
    }
}
