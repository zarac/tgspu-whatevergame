package whatevergame.services.test.client;

import whatevergame.communication.Connection;

import whatevergame.services.ClientService;
import whatevergame.services.Package;

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

    /**
     * Prints out the received package and sends back a pong message.
     * {@inheritDoc}
     * @see Service#receivePackage(Package)
     */
    //public void receivePackage(Package p_package)
    //{
        //logger.debug("Received package '" + p_package.toString() + "'");
        //logger.debug("Sending return test package..");
        ////send(new Package(new Content("Hello right back at ya! ;)"), id));
        //send(new Content("Hello right back at ya! ;)"));
    //}

    // TODO : Shouldn't need to cast
    public void receive(whatevergame.services.Content content)
    {
        logger.debug("Received content\n    [" + (whatevergame.services.test.Content)content + "]");
        logger.debug("Sending return test package..");
        //send(new Package(new Content("Hello right back at ya! ;)"), id));
        send(new Content("Hello right back at ya! ;)"));
    }
}
