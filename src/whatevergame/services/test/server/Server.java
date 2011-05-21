package whatevergame.services.test.server;

import logging.Logger;

import whatevergame.server.Client;

import whatevergame.services.Package;
import whatevergame.services.ServerService;

import whatevergame.services.test.Content;

/**
 * A test server service. Greets added clients and prints incoming packages.
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
public class Server extends ServerService
{
    /**
     * A logger, it's handy to have.
     */
    protected Logger logger;

    /**
     * {@inheritDoc}
     * @see ServerService#Server(int)
     */
    public Server(int id)
    {
        super(id);

        logger = new Logger(this);
    }

    /**
     * Prints the received package.
     *
     * {@inheritDoc}
     * @see whatevergame.services.Service#receivePackage(Package)
     */
    public void receivePackage(Package p_package)
    {
        logger.debug("Received package '" + p_package.toString() + "'");
    }

    /**
     * Says hello to added clients and tells them the service ID.
     *
     * {@inheritDoc}
     * @see ServerService#addClient(Client)
     */
    public void addClient(Client client)
    {
        super.addClient(client);
        logger.info("Sending test package to client '" + client + "'.");
        client.send(new Package(new Content("test package #1"), id));
        client.send(new Package(new Content("test package #2"), id));
        client.send(new Package(new Content("test package #3"), id));

        // TODO : Why are parenthesis needed?
        client.send(new Package(new Content("Hello " + (client.getIpAddress()) + "! id='" + id + "'"), id));
    }
}
