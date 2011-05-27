package whatevergame.services;

import logging.Logger;

import whatevergame.communication.Connection;

/**
 * Defines a service and THE services used for Whatever Game.
 *
 * TODO : Not hard-code service IDs.
 * TODO : Does a service really need to know its own id?
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
abstract public class Service
{
    // TODO : Javadoc or remove
    public final static int NO_SERVICE_ID = -1;
    public final static int COUNT = 8;

    public final static int LOGIN = 0;
    public final static int CHAT = 1;
    public final static int PEWPEW = 2;
    public final static int FIVEPAD = 3;
    public final static int TEST = 4;
    public final static int MOTD = 5;
    public final static int LOBBY = 6;
    public final static int SCORE = 7;

    /**
     * A logger, it's handy to have.
     */
    protected Logger logger;

    /**
     * ID of the service.
     */
    protected int id;

    /**
     * Creates an implemented Service.
     * 
     * @param id The ID of the service.
     */
    public Service(int id)
    {
        this.id = id;
        
        logger = new Logger(this);
    }

    /**
     * Handle a received package.
     * 
     * @param p_package The package.
     */
    abstract public void receivePackage(Connection connection, Package p_package);

    /**
     * Gets the id for this instance.
     *
     * @return The id.
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * {@inheritDoc}
     * @see Object#toString()
     */
    public String toString()
    {
        return "Service[id=" + id + "]";
    }
}
