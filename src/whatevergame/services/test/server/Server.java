package whatevergame.services.test.server;

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
     * {@inheritDoc}
     * @see ServerService#Server(int)
     */
    public Server(int id, whatevergame.server.Server server)
    {
        super(id, server);
    }

    /**
     * Prints the received package.
     *
     * TODO : Shouldn't need to cast. login.Content is Content!
     *
     * {@inheritDoc}
     * @see whatevergame.services.Service#receivePackage(Package)
     */
    public void receive(Client client, whatevergame.services.Content p_content)
    {
        Content content = (Content)p_content;
        logger.debug("Received content '" + content + "' from client '" + client + "'.");
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
        logger.info("Sending 3 test packages to client '" + client + "'.");
        send(client, new Content("test package #1"));
        send(client, new Content("test package #2"));
        send(client, new Content("test package #3"));

        // TODO : Why are parenthesis needed?
        send(client, new Content("Hello " + (client.getIpAddress()) + "! id='" + id + "'"));
    }
}
