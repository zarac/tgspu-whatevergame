package whatevergame.services;

import whatevergame.client.Client;

import whatevergame.communication.Connection;

/**
 * The client side of a service should extend this.
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
abstract public class ClientService extends Service
{
    /**
     * A reference to the main client.
     */
    protected Client client;

    /**
     * The connection to the server.
     */
    protected Connection connection;

    /**
     * Create an instance of a ClientService implementation.
     * 
     * @param id The ID of the service.
     * @param connection The connection to the server.
     */
    public ClientService(int id, Client client)
    {
        super(id);

        this.client = client;
        this.connection = client.getConnection();
    }

    /**
     * Packages content forwards it to the connecting for queued sending.
     * 
     * @param content The package to be sent.
     */
    public void send(Content content)
    {
        connection.send(new Package(content, id));
    }

    public void receivePackage(Connection connection, Package p_package)
    {
        receive(p_package.getContent());
    }

    abstract public void receive(Content content);
}
