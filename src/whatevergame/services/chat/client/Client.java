package whatevergame.services.chat.client;

import whatevergame.services.ClientService;

import whatevergame.services.chat.Content;

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

    // TODO : Shouldn't need to cast
    public void receive(whatevergame.services.Content p_content)
    {
        Content content = (Content)content;
        logger.debug("Received content\n    [" + content + "]");
    }
}
