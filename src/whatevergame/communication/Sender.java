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

    protected int WAIT_TIME = 100;

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

        this.oos = oos;
        sentCount = 0;
        failedCount = 0;
        queue = new LinkedList<Package>();
        worker = new Worker();
    }

    /**
     * Adds a package to a buffer and starts a worker thread (unless already running).
     * 
     * @param p_package The package to send.
     */
    synchronized public void enqueue(Package p_package)
    {
        logger.debug("Queuing package:\n    [" + p_package + "]");

        queue.add(p_package);

        // TODO : Fix IllegalThreadStateException "bug"
        if (!worker.isAlive())
            worker.start();
            //worker.start();
    }

    /**
     * Used internally to send a package. Writes a package to the stream. Keeps a counter of successful and unsuccessful deliveries.
     * 
     * @param p_package The package to send.
     * @return If successful.
     */
    protected boolean send(Package p_package)
    {
        logger.debug("trying to send package:\n    [" + p_package + "]");
        try
        {
            oos.writeObject(p_package);
            logger.debug("Package sent!");
            sentCount++;
            return true;
        }
        catch (IOException e)
        {
            logger.error("Failed writing object to output stream (" + e.getMessage() + ").");
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
            logger.debug("Starting send worker.\n    [queue size='" + queue.size() + "']");
            isRunning = true;
            while (isRunning)
            {
                if (!queue.isEmpty())
                {
                    Package thePackage = queue.remove();
                    logger.debug("sending package\n    [" + thePackage + "]");
                    send(thePackage);
                    logger.debug("Remaining packages in queue\n    [" + queue.size() + "]");
                }
                else
                {
                    try
                    {
                        //logger.info("queue was empty, sleeping for " + WAIT_TIME + " seconds");
                        sleep(WAIT_TIME);
                    }
                    catch (InterruptedException e)
                    {
                        logger.warning("thread was interrupted while sleeping");
                    }
                    //isRunning = false;
                }
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
