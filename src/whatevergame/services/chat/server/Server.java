package whatevergame.services.chat.server;

import whatevergame.server.Client;

import whatevergame.services.ServerService;

import whatevergame.services.chat.Content;

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

    public void receive(Client client, whatevergame.services.Content p_content)
    {
        Content content = (Content)p_content;
        logger.debug("Received content '" + content + "' from client '" + client + "'.");
    }
}
