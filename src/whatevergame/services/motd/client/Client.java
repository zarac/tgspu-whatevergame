package whatevergame.services.motd.client;

import whatevergame.communication.Connection;

import whatevergame.services.ClientService;

import whatevergame.services.motd.Content;

public class Client extends ClientService
{
    public Client(int id, Connection connection)
    {
        super(id, connection);
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
