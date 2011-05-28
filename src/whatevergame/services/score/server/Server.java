package whatevergame.services.score.server;

import whatevergame.server.Client;
import whatevergame.server.User;

import whatevergame.services.ServerService;

import whatevergame.services.score.Content;

public class Server extends ServerService
{
    protected Database database;

    public Server(int id, whatevergame.server.Server server)
    {
        super(id, server);

        database = new Database();
    }

    public void receive(Client client, whatevergame.services.Content p_content)
    {
        Content content = (Content)p_content;
        logger.debug("Received content '" + content + "' from client '" + client + "'.");
    }

    public void add(User user, int amount)
    {
        database.add(user, amount);

        updateClients();
    }

    public void updateClients()
    {
        String score = database.getAll();

        for (Client client : clients)
            send(client, new Content(score));
    }

    public void addClient(Client client)
    {
        super.addClient(client);

        send(client, new Content(database.getAll()));
    }
}
