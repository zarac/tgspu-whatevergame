package whatevergame.client;

import java.io.IOException;

import java.net.Socket;
import java.net.UnknownHostException;

import logging.Logger;

import whatevergame.communication.Connection;

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
    protected Service[] services;
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
        services = new Service[Service.COUNT];
        System.out.println("HELLO?");
        connection = new Connection();
        System.out.println("HELLO!");

        initServices();
        connect();
    }

    /**
     * Initializes the services.
     */
    public void initServices()
    {
        services[Service.TEST] = new whatevergame.services.test.client.Client(Service.TEST, connection);
        services[Service.LOGIN] = new whatevergame.services.login.client.Client(Service.LOGIN, connection);
        services[Service.MOTD] = new whatevergame.services.motd.client.Client(Service.MOTD, connection);
        services[Service.FIVEPAD] = new whatevergame.services.fivepad.client.Client(Service.FIVEPAD, connection);
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
}
