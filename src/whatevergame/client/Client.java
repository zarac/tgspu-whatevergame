package whatevergame.client;

import java.io.IOException;

import java.net.Socket;
import java.net.UnknownHostException;

import logging.Logger;

import whatevergame.communication.Connection;

import whatevergame.services.ClientService;
import whatevergame.services.Service;

/**
 * The client version of Whatever Game.
 *
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
public class Client
{
    // TODO : javadoc
    protected Connection connection;
    protected ClientService[] services;
    protected String serverIp;
    protected int serverPort;
    
    protected Logger logger;

    /**
     * Creates an instance of a client.
     * TODO : Local port should not be fixed to 3000.
     * 
     * @param ip Address to connect to.
     * @param port Port to connect to.
     */
    public Client(String ip, int port)
    {
        serverIp = ip;
        serverPort = port;

        logger = new Logger(this);
        //services = new ClientService[Service.COUNT];
        connection = new Connection();

        initServices();

        // depends on services being initialized
        connect();
    }

    /**
     * Initializes the services.
     */
    public void initServices()
    {
        services = new ClientService[Service.COUNT];
        services[Service.LOGIN] = new whatevergame.services.login.client.Client(Service.LOGIN, this);
        services[Service.CHAT] = new whatevergame.services.chat.client.Client(Service.CHAT, this);
        services[Service.PEWPEW] = new whatevergame.services.pewpew.client.Client(Service.PEWPEW, this);
        services[Service.FIVEPAD] = new whatevergame.services.fivepad.client.Client(Service.FIVEPAD, this);
        services[Service.TEST] = new whatevergame.services.test.client.Client(Service.TEST, this);
        services[Service.MOTD] = new whatevergame.services.motd.client.Client(Service.MOTD, this);
        services[Service.LOBBY] = new whatevergame.services.lobby.client.Client(Service.LOBBY, this);
        services[Service.SCORE] = new whatevergame.services.score.client.Client(Service.SCORE, this);
    }

    /**
     * Tries to connect to the server.
     * 
     * @return If successful or not.
     */
    public boolean connect()
    {
        logger.debug("connecting...");
        // -1 means no session ID
        try
        {
            //connection = new Connection(services, new Socket(serverIp, serverPort), -1);
            connection.init(services, new Socket(serverIp, serverPort), -1);
            connection.connect();
            logger.info("Connected to server '" + serverIp + ":" + serverPort + "', yay!");
            return true;
        }
        catch (UnknownHostException e)
        {
            logger.error("Could not create socket, unknown host (" + e.getMessage() + ").");
            return false;
        }
        catch (IOException e)
        {
            logger.error("Could not create socket (" + e.getMessage() + ").");
            return false;
        }
    }

    /**
     * Gets the connection for this instance.
     *
     * @return The connection.
     */
    public Connection getConnection()
    {
        return this.connection;
    }
}
