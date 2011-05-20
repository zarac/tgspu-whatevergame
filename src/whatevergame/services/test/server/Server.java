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
        logger.debug("receivePackage(" + p_package.toString() + "):");
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
        logger.info("addClient(): Sending test package" + client);
        // TODO : Why are parenthesis needed?
        client.send(new Package(new Content("Hello " + (client.getIpAddress()) + "! id='" + id + "'"), id));
    }
}
