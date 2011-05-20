package whatevergame.communication;

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
    public Receiver(Service[] services, ObjectInputStream ois)
    {
        this.services = services;
        this.ois = ois;
        logger = new Logger(this);
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
                logger.debug("Received package '" + _package + "'., Sending to service '" + _package.getServiceId() + "'.");
                services[_package.getServiceId()].receivePackage(_package);
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
