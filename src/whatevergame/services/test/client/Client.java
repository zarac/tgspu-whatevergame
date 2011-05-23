package whatevergame.services.test.client;

import whatevergame.communication.Connection;

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
     * @see ClientService#Client(int,Connection)
     */
    public Client(int id, Connection connection)
    {
        super(id, connection);
    }

    // TODO : Shouldn't need to cast
    public void receive(whatevergame.services.Content content)
    {
        logger.debug("Received content\n    [" + (whatevergame.services.test.Content)content + "]");
        logger.debug("Sending return test package..");
        send(new Content("Hello right back at ya! ;)"));
    }
}
