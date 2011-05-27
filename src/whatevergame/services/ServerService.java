package whatevergame.services;

import java.util.LinkedList;

import whatevergame.communication.Connection;

import whatevergame.server.Client;
import whatevergame.server.Server;

/**
 * The server side of a service should extend this.
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
abstract public class ServerService extends Service
{
    protected Server server;
    protected ServiceProvider services;

    /**
     * The clients using this service.
     */
    protected LinkedList<Client> clients;

    /**
     * {@inheritDoc}
     * @see Service#ServerService(int)
     */
    public ServerService(int id, Server server)
    {
        super(id);
        this.server = server;
        services = server.getServices();
        clients = new LinkedList<Client>();
    }

    /**
     * Add a client to the service.
     * 
     * @param client The client to be added.
     */
    public void addClient(Client client)
    {
        clients.add(client);
    }

    /**
     * Removes a client from the service.
     * 
     * @param client The client to be removed.
     */
    public void removeClient(Client client)
    {
        clients.remove(client);
    }

    /**
     * Packages content and sends it to client.
     * 
     * @param client The client.
     * @param content The content.
     */
    public void send(Client client, Content content)
    {
        client.send(new Package(content, id));
    }

    // TODO : Why is cast needed? Client is a Connection
    public void receivePackage(Connection connection, Package p_package)
    {
        receive((Client)connection, p_package.getContent());
    }

    abstract public void receive(Client client, Content content);

    public ServerService getService(int id)
    {
        return (ServerService)server.getServices().get(id);
    }
}
