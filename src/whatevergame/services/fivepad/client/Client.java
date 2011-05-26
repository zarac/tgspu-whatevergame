package whatevergame.services.fivepad.client;

import whatevergame.services.ClientService;

import whatevergame.services.fivepad.Content;

public class Client extends ClientService
{
    /**
     * {@inheritDoc}
     * @see ClientService#Client(int,Client)
     */
    public Client(int id, whatevergame.client.Client client)
    {
        super(id, client);
    }

    /**
     * {@inheritDoc}
     * @see ClientService#receive(Content)
     */
    // TODO : Shouldn't need to cast.
    public void receive(whatevergame.services.Content p_content)
    {
        Content content = (Content)p_content;
        logger.info(content.toString());
    }
}
