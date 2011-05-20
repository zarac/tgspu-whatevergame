package whatevergame.services;

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
    public final static int COUNT = 5;
    public final static int LOGIN = 0;
    public final static int CHAT = 1;
    public final static int PEWPEW = 2;
    public final static int FIVEPAD = 3;
    public final static int TEST = 4;

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
    }

    /**
     * Handle a received package.
     * 
     * @param p_package The package.
     */
    abstract public void receivePackage(Package p_package);

    /**
     * Gets the id for this instance.
     *
     * @return The id.
     */
    public int getId()
    {
        return this.id;
    }
}
