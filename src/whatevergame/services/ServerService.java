package whatevergame.services;

import java.util.LinkedList;

import whatevergame.server.Client;

/**
 * The server side of a service should extend this.
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
abstract public class ServerService extends Service
{
    /**
     * The clients using this service.
     */
    protected LinkedList<Client> clients;

    /**
     * {@inheritDoc}
     * @see Service#ServerService(int)
     */
    public ServerService(int id)
    {
        super(id);
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
}
