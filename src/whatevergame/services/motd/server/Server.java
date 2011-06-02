package whatevergame.services.motd.server;

import whatevergame.server.Client;

import whatevergame.services.motd.Content;

import whatevergame.services.ServerService;

public class Server extends ServerService
{
    protected Database database;

    protected String motd;

    public Server(int id, whatevergame.server.Server server)
    {
        super(id, server);
        database = new Database();
    }

    /**
     * {@inheritDoc}
     * @see ServerService#receive(Client,Content)
     */
    public void receive(Client client, whatevergame.services.Content p_content)
    {
        //logger.info("client sent us motd = " + content);
        send(client, new Content(database.getLatestMotd()));
    }

    @Override
    public void addClient(Client client)
    {
        super.addClient(client);

        send(client, new Content(database.getLatestMotd()));
    }
}
