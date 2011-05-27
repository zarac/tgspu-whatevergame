package whatevergame.server;

import java.util.LinkedList;

import logging.Logger;

import whatevergame.services.Service;
import whatevergame.services.ServerService;
import whatevergame.services.ServiceProvider;

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
    //protected ServerService[] services;
    protected ServiceProvider services;

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
        services = new ServiceProvider();

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
        services.init(Service.COUNT);
        services.add(Service.LOGIN, new whatevergame.services.login.server.Server(Service.LOGIN, this));
        services.add(Service.CHAT, new whatevergame.services.chat.server.Server(Service.CHAT, this));
        services.add(Service.PEWPEW, new whatevergame.services.pewpew.server.Server(Service.PEWPEW, this));
        services.add(Service.FIVEPAD, new whatevergame.services.fivepad.server.Server(Service.FIVEPAD, this));
        services.add(Service.TEST, new whatevergame.services.test.server.Server(Service.TEST, this));
        services.add(Service.MOTD, new whatevergame.services.motd.server.Server(Service.MOTD, this));
        services.add(Service.LOBBY, new whatevergame.services.lobby.server.Server(Service.LOBBY, this));
        services.add(Service.SCORE, new whatevergame.services.score.server.Server(Service.SCORE, this));
    }

    //public void initServices()
    //{
        //services = new ServerService[Service.COUNT];
        //services[Service.LOGIN] = new whatevergame.services.login.server.Server(Service.LOGIN, this);
        //services[Service.CHAT] = new whatevergame.services.chat.server.Server(Service.CHAT, this);
        //services[Service.PEWPEW] = new whatevergame.services.pewpew.server.Server(Service.PEWPEW, this);
        //services[Service.FIVEPAD] = new whatevergame.services.fivepad.server.Server(Service.FIVEPAD, this);
        //services[Service.TEST] = new whatevergame.services.test.server.Server(Service.TEST, this);
        //services[Service.MOTD] = new whatevergame.services.motd.server.Server(Service.MOTD, this);
        //services[Service.LOBBY] = new whatevergame.services.lobby.server.Server(Service.LOBBY, this);
        //services[Service.SCORE] = new whatevergame.services.score.server.Server(Service.SCORE, this);
    //}

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
     * Gets the service provider for this instance.
     *
     * @return The service provider.
     */
    public ServiceProvider getServices()
    {
        return services;
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
        ((ServerService)services.get(Service.MOTD)).addClient(client);
        ((ServerService)services.get(Service.LOGIN)).addClient(client);
    }

    public void removeClient(Client client)
    {
        logger.debug("removed client '" + client + "'");
        clients.remove(client);
        // TODO : remove client from all services!
    }
}
