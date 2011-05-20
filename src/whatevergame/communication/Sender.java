package whatevergame.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;

import java.util.LinkedList;

import logging.Logger;

import whatevergame.services.Package;

/**
 * Used for sending over the network (or locally).
 *
 * TODO : synchronize (thread safe)
 * TODO : Graceful shut down (all threads).
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
public class Sender
{
    // TODO : javadoc
    protected ObjectOutputStream oos;
    protected int sentCount;
    protected int failedCount;
    protected LinkedList<Package> queue;
    protected Worker worker;

    /**
     * A logger, it's handy to have.
     */
    protected Logger logger;

    /**
     * Creates a Sender.
     * 
     * @param oos The stream to send to.
     */
    public Sender(ObjectOutputStream oos)
    {
        logger = new Logger(this);
        sentCount = 0;
        failedCount = 0;
        queue = new LinkedList<Package>();
        worker = new Worker();
        this.oos = oos;
    }

    /**
     * Adds a package to a buffer and starts a worker thread (unless already running).
     * 
     * @param p_package The package to send.
     */
    public void enQueue(Package p_package)
    {
        queue.add(p_package);

        if (!worker.isRunning())
            worker.start();
    }

    /**
     * Used internally to send a package. Writes a package to the stream. Keeps a counter of successful and unsuccessful deliveries.
     * 
     * @param p_package The package to send.
     * @return If successful.
     */
    protected boolean send(Package p_package)
    {
        logger.debug("Sender.send(" + p_package + "): Trying to send...");
        try
        {
            oos.writeObject(p_package);
            logger.debug("Sender.send(): Package sent!");
            sentCount++;
            return true;
        }
        catch (IOException e)
        {
            logger.error("Sender.send(): ERROR: Failed writing object to output stream (" + e.getMessage() + ").");
            failedCount++;
            return false;
        }
    }

    /**
     * A worker thread for sending queued packages.
     */
    protected class Worker extends Thread
    {
        protected boolean isRunning;

        /**
         * {@inheritDoc}
         * @see Runnable#run()
         */
        public void run()
        {
            isRunning = true;
            while (isRunning)
            {
                if (!queue.isEmpty())
                    send(queue.remove());
                else
                    isRunning = false;
            }
        }

        /**
         * Determines if this instance is isRunning.
         *
         * @return The isRunning.
         */
        public boolean isRunning()
        {
            return isRunning;
        }
    }
}
