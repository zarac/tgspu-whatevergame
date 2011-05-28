package whatevergame.communication;

import java.io.InputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;

import java.util.LinkedList;

import logging.Logger;

import whatevergame.services.Package;
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
    public final static int NO_SESSION_ID = -1;

    protected Sender sender;
    protected Receiver receiver;
    protected Socket socket;
    protected int sessionId = NO_SESSION_ID;
    protected ServiceProvider services;
    protected DisconnectionHandler disconnectionHandler;

    /**
     * A logger, it's handy to have.
     */
    protected Logger logger;

    /**
     * Constructs a new instance.
     *
     * @param services Services used for routing incoming packages.
     * @param disconnectionHandler Will be called upon disconnection.
     */
    public Connection(ServiceProvider services, DisconnectionHandler disconnectionHandler)
    {
        logger = new Logger(this);
        this.services = services;
        this.disconnectionHandler = disconnectionHandler;
    }

    /**
     * Set the socket. Needs to be called before connect() is called.
     * 
     * @param socket The socket used for communication.
     */
    public void setSocket(Socket socket)
    {
        this.socket = socket;
    }

    /**
     * Tries to connect and create a sender and receiver.
     * 
     * @return If successful or not.
     */
    public boolean connect(Socket socket)
    {
        this.socket = socket;

        // TODO : clean up!
        logger.debug("Trying to set up connection...");
        logger.debug("connect(): services=" + services);
        try
        {
            sender = new Sender(new ObjectOutputStream(socket.getOutputStream()));
            logger.info("Created Sender");
            // TODO : ? time out waiting for inputStream
            receiver = new Receiver();
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
     * Sets the sessionId for this instance.
     *
     * @param sessionId The sessionId.
     */
    public void setSessionId(int sessionId)
    {
        this.sessionId = sessionId;
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
        protected ObjectInputStream objectInputStream;

        /**
         * The status.
         */
        protected boolean active;

        /**
         * A logger, it's handy to have.
         */
        protected Logger logger;

        /**
         * Creates instance of Receiver.
         * 
         * @param connection Connection used to create receiver.
         */
        public Receiver()
        {
            super("ReceiverForSessionId:" + getSessionId());
            logger = new Logger(this);
            logger.debug("Receiver(): services=" + services);
        }

        /**
         * Initializes the receiver.
         * 
         * @throws IOException
         */
        public void init() throws IOException
        {
            logger.debug("Trying to set up receiver...");
            InputStream stream = getSocket().getInputStream();
            logger.debug("Got InputStream");
            objectInputStream = new ObjectInputStream(stream);
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
            if (services == null)
            {
                logger.error("stopping: services = null");
                return;
            }
            else
                logger.debug("services=" + services);

            active = true;
            while (active)
            {
                logger.debug("Waiting for next package...");
                try
                {
                    Package _package = (Package)objectInputStream.readObject();

                    logger.debug("received package\n    [" + _package + "]");
                    logger.debug("routing to service: " + services.get(_package.getServiceId()));
                    // TODO : Should add to buffer!
                    services.get(_package.getServiceId()).receivePackage(Connection.this, _package);
                }
                catch (IOException e)
                {
                    logger.debug("could not read object from object input stream (" + e.getMessage() + ").");
                    active = false;
                    disconnectionHandler.disconnected(Connection.this);
                }
                catch (ClassNotFoundException e)
                {
                    logger.error("class not found (" + e.getMessage() + ").");
                    active = false;
                    disconnectionHandler.disconnected(Connection.this);
                }
            }
        }
    }

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
        protected ObjectOutputStream objectOutputStream;
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
         * @param objectOutputStream The stream to send to.
         */
        public Sender(ObjectOutputStream objectOutputStream)
        {
            logger = new Logger(this);

            this.objectOutputStream = objectOutputStream;
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
                objectOutputStream.writeObject(p_package);
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
                            // TODO : use wait(..) instead of sleep(..)
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
}
