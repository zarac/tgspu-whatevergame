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
    // Database settings
    protected final int dbPort = 3306;
    protected final String dbHost = "195.178.232.7";
    protected final String dbUsername = "DA211T10C4062119";
    protected final String dbPassword = "4062119";
    protected final String dbDatabase = "da211t10c4062119";

    protected int port = 3000;
    protected ConnectionCreator connectionCreator;
    // TODO : ? Use better data structure.
    protected LinkedList<Client> clients;
    protected ServerService[] services;

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

        setupDatabase();
        initServices();
    }

    public void initServices()
    {
        services = new ServerService[Service.COUNT];
        services[Service.LOGIN] = new whatevergame.services.login.server.Server(Service.LOGIN, this);
        services[Service.CHAT] = new whatevergame.services.chat.server.Server(Service.CHAT, this);
        services[Service.PEWPEW] = new whatevergame.services.pewpew.server.Server(Service.PEWPEW, this);
        services[Service.FIVEPAD] = new whatevergame.services.fivepad.server.Server(Service.FIVEPAD, this);
        services[Service.TEST] = new whatevergame.services.test.server.Server(Service.TEST, this);
        services[Service.MOTD] = new whatevergame.services.motd.server.Server(Service.MOTD, this);
        services[Service.LOBBY] = new whatevergame.services.lobby.server.Server(Service.LOBBY, this);
        services[Service.SCORE] = new whatevergame.services.score.server.Server(Service.SCORE, this);
    }

    public void setupDatabase()
    {
        MySqlDatabase database = new MySqlDatabase(dbHost, dbPort, dbUsername, dbPassword, dbDatabase);
        database.connect();
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

    /**
     * Add a client to a list of connected clients.
     * 
     * @param client The client
     */
    public void addClient(Client client)
    {
        logger.info("adding client '" + client + "'");
        clients.add(client);

        // initial services for connected clients
        services[Service.MOTD].addClient(client);
        services[Service.LOGIN].addClient(client);
    }

    public void removeClient(Client client)
    {
        logger.debug("removed client '" + client + "'");
        clients.remove(client);
        // TODO : remove client from all services!
    }
}
