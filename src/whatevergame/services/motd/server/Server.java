package whatevergame.services.motd.server;

import whatevergame.server.Client;

import whatevergame.services.motd.Content;

import whatevergame.services.ServerService;

public class Server extends ServerService
{
    public Server(int id)
    {
        super(id);
    }

    /**
     * {@inheritDoc}
     * @see ServerService#receive(Client,Content)
     */
    public void receive(Client client, whatevergame.services.Content p_content)
    {
        Content content = (Content)p_content;
        logger.info("client sent us motd = " + content);
        send(client, new Content("TODAY THE WEATHER IS RAINY, NOT!"));
    }

    @Override
    public void addClient(Client client)
    {
        super.addClient(client);
        logger.debug("Sending welcome message to client.");
        send(client, new Content("WELCOME TODAY IS RAINY, NOT!"));
    }
}
