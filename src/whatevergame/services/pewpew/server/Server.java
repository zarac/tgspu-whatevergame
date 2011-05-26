package whatevergame.services.pewpew.server;

import whatevergame.server.Client;

import whatevergame.services.ServerService;

import whatevergame.services.pewpew.Content;

public class Server extends ServerService
{
    public Server(int id, whatevergame.server.Server server)
    {
        super(id, server);
    }

    public void receive(Client client, whatevergame.services.Content p_content)
    {
        Content content = (Content)p_content;
    }
}
