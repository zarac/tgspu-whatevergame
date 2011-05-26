package whatevergame.services.lobby.server;

import whatevergame.server.Client;

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
        Content content = (Content)p_content;
    }

    /**
     * {@inheritDoc}
     * @see ServerService#addClient(Client)
     */
    //public void addClient(Client client)
    //{
        //super.addClient(client);
    //}
}
