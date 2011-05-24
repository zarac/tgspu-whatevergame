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

        services = new ServerService[Service.COUNT];
        services[Service.TEST] = new whatevergame.services.test.server.Server(Service.TEST);
        services[Service.LOGIN] = new whatevergame.services.login.server.Server(Service.LOGIN);
        services[Service.MOTD] = new whatevergame.services.motd.server.Server(Service.MOTD);
        services[Service.FIVEPAD] = new whatevergame.services.fivepad.server.Server(Service.FIVEPAD);
    }

    public void setupDatabase()
    {
        // database connection
        logger.debug("Connecting to database...");
        String host = "195.178.232.7";
        int port = 3306;
        String username = "DA211T10C4062119";
        String password = "4062119";
        String databaseName = "da211t10c4062119";
        MySqlDatabase database = new MySqlDatabase(host, port, username, password, databaseName);
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

        // TODO : remove, just testing some..
        logger.comment("adding client to test service and login service and MOTD service!");
        services[Service.TEST].addClient(client);
        services[Service.LOGIN].addClient(client);
        services[Service.MOTD].addClient(client);
        services[Service.FIVEPAD].addClient(client);
    }
}
