package whatevergame.services.fivepad.server;

import whatevergame.server.Client;

import whatevergame.services.fivepad.Content;

import whatevergame.services.ServerService;

public class Server extends ServerService
{
    public Server(int id, whatevergame.server.Server server)
    {
        super(id, server);
    }

    /**
     * {@inheritDoc}
     * @see ServerService#receive(Client,Content)
     */
    public void receive(Client client, whatevergame.services.Content p_content)
    {
        Content content = (Content)p_content;
        logger.info("client sent us fivepad content '" + content + "'");
        send(client, new Content("5Pad response"));
    }

    @Override
    public void addClient(Client client)
    {
        super.addClient(client);
        logger.info("Client added to five pad service..");
    }
}
