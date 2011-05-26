package whatevergame.services.score.client;

import whatevergame.services.ClientService;

import whatevergame.services.score.Content;

public class Client extends ClientService
{
    public Client(int id, whatevergame.client.Client client)
    {
        super(id, client);
    }

    // TODO : Shouldn't need to cast
    public void receive(whatevergame.services.Content p_content)
    {
        Content content = (Content)p_content;
    }
}
