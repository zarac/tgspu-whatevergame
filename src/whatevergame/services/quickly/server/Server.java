package whatevergame.services.quickly.server;

import whatevergame.server.Client;

import whatevergame.services.Package;
import whatevergame.services.ServerService;

import whatevergame.services.quickly.Content;

/**
 * The quickly server service. Be quick to hit the targets.
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
     * {@inheritDoc}
     * @see whatevergame.services.Service#receivePackage(Package)
     */
    public void receive(Client client, whatevergame.services.Content p_content)
    {
        Content content = (Content)p_content;
        logger.debug("Received content '" + content + "' from client '" + client + "'.");
    }

    /**
     * {@inheritDoc}
     * @see ServerService#addClient(Client)
     */
    public void addClient(Client client)
    {
        super.addClient(client);
        send(client, new Content("quickly package"));
    }
}
