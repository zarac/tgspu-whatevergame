package whatevergame.services.test.client;

import whatevergame.services.ClientService;

import whatevergame.services.test.Content;

/**
 * The testing client. It responds to a received message.
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
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
    public void receive(whatevergame.services.Content content)
    {
        logger.debug("Received content\n    [" + (whatevergame.services.test.Content)content + "]");
        logger.debug("Sending return test package..");
        send(new Content("Hello right back at ya! ;)"));
    }
}
