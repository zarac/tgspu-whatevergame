package whatevergame.services;

import logging.Logger;

import whatevergame.communication.Connection;

/**
 * The client side of a service should extend this.
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
abstract public class ClientService extends Service
{
    /**
     * The connection to the server.
     */
    protected Connection connection;

    /**
     * A logger, it's handy to have.
     */
    protected Logger logger;

    /**
     * Create an instance of a ClientService implementation.
     * 
     * @param id The ID of the service.
     * @param connection The connection to the server.
     */
    public ClientService(int id, Connection connection)
    {
        super(id);
        
        logger = new Logger(this);

        this.connection = connection;
    }

    /**
     * Forwards a package to the connecting for queued sending.
     * 
     * @param p_package The package to be sent.
     */
    public void send(Package p_package)
    {
        connection.send(p_package);
    }
}
