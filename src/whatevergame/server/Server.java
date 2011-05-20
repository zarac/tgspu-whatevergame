package whatevergame.server;

import java.util.LinkedList;

import logging.Logger;

import whatevergame.services.Service;
import whatevergame.services.ServerService;

/**
 * The Whatever Game server.
 * TODO : javadoc!
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
public class Server
{
    protected int port;
    protected ConnectionCreator connectionCreator;
    // TODO : Use better data structure.
    protected LinkedList<Client> clients;
    // TODO : protected (just testing for now)
    public ServerService[] services;

    /**
     * A logger, it's handy to have.
     */
    protected Logger logger;

    public Server(int port)
    {
        logger = new Logger(this);

        this.port = port;
        connectionCreator = new ConnectionCreator(this);
        clients = new LinkedList<Client>();

        if (connectionCreator.init())
        {
            logger.info("Socket opened at port '" + port + "'.");
            connectionCreator.start();
            logger.info("Server up and running! :)");
        }
        else
            logger.error("Could not start server! :(");

        services = new ServerService[Service.COUNT];
        services[Service.TEST] = new whatevergame.services.test.server.Server(Service.TEST);
    }

    /**
     * Gets the port for this instance.
     *
     * @return The port.
     */
    public int getPort()
    {
        return this.port;
    }

    /**
     * Gets the services for this instance.
     *
     * @return The services.
     */
    public ServerService[] getServices()
    {
        return this.services;
    }

    public void addClient(Client client)
    {
        clients.add(client);
    }
}