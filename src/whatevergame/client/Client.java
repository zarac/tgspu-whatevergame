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
 * TODO : synchronize (thread safe)
 * TODO : Graceful shut down (all threads).
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
     * TODO : Port for incoming communication. Should not be fixed to 3000.
     * 
     * @param ip Address to connect to.
     * @param port Port to connect to.
     */
    public Client(String ip, int port)
    {
        logger = new Logger(this);

        services = new Service[Service.COUNT];

        serverIp = ip;
        serverPort = port;
        connect();

        // Register services
        // TODO : ? ClientService should take Client instead so connection can be initiated afterwards services.
        services[Service.TEST] = new whatevergame.services.test.client.Client(Service.TEST, connection);
        services[Service.LOGIN] = new whatevergame.services.login.client.Client(Service.LOGIN, connection);
    }

    /**
     * Tries to connect to the server.
     * 
     * @return
     */
    public boolean connect()
    {
        logger.debug("connecting...");
        // -1 means no session ID
        try
        {
            connection = new Connection(services, new Socket(serverIp, serverPort), -1);
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
