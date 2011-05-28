package whatevergame.services.lobby.server;

import whatevergame.server.Client;

import whatevergame.services.ExitContent;
import whatevergame.services.ServerService;

import whatevergame.services.lobby.Content;

public class Server extends ServerService
{
    public Server(int id, whatevergame.server.Server server)
    {
        super(id, server);
    }

    // TODO : Shouldn't need to cast.
    public void receive(Client client, whatevergame.services.Content p_content)
    {
        if (p_content instanceof ExitContent)
            removeClient(client);
        else if (p_content instanceof Content)
        {
            Content content = (Content)p_content;
            logger.info("content=" + content);
        }
        else
            logger.warning("unknown content");
    }

    /**
     * {@inheritDoc}
     * @see ServerService#addClient(Client)
     */
    public void addClient(Client client)
    {
        super.addClient(client);
        getService(CHAT).addClient(client);
        getService(SCORE).addClient(client);
    }

    /**
     * {@inheritDoc}
     * @see ServerService#removeClient(Client)
     */
    public void removeClient(Client client)
    {
        super.removeClient(client);
        getService(CHAT).removeClient(client);
        getService(SCORE).removeClient(client);
        getService(PEWPEW).removeClient(client);
        getService(FIVEPAD).removeClient(client);
    }
}
