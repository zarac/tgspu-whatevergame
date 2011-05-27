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
    //protected ClientService[] services;

    /**
     * The connection to the server.
     */
    //protected Connection connection;

    abstract public void receive(Content content);

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
        //services = client.getServices();
        //connection = client.getConnection();
    }

    /**
     * Packages content forwards it to the connecting for queued sending.
     * 
     * @param content The package to be sent.
     */
    public void send(Content content)
    {
        client.getConnection().send(new Package(content, id));
    }

    public void receivePackage(Connection connection, Package p_package)
    {
        receive(p_package.getContent());
    }

    /**
     * Enables the service.
     * TODO : ? abstract
     */
    public void enable()
    {
    }

    /**
     * Disables the service.
     * TODO : ? abstract
     */
    public void disable()
    {
    }
}
