package whatevergame.services.chat.server;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import whatevergame.server.Client;

import whatevergame.services.chat.UsersContent;

import whatevergame.services.ServerService;

import whatevergame.services.chat.Content;

/**
 * A test server service. Greets added clients and prints incoming packages.
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
public class Server extends ServerService
{
    /**
     * Date format.
     */
    protected static final String DATE_FORMAT = "HH:mm:ss";

    /**
     * Date format.
     */
    protected static SimpleDateFormat dateFormat = new
        SimpleDateFormat(DATE_FORMAT);

    /**
     * {@inheritDoc}
     * @see ServerService#Server(int)
     */
    public Server(int id, whatevergame.server.Server server)
    {
        super(id, server);
    }

    public void receive(Client sender, whatevergame.services.Content p_content)
    {
        Content content = (Content)p_content;
        logger.debug("Received content '" + content + "' from client '" + sender + "'.");

        String time = dateFormat.format(Calendar.getInstance().getTime());

        for (Client client : clients)
            send(client, new Content("[ " + time + " | " + sender.getUser().getUsername() + " ] " + content.getMessage()));
    }

    /**
     * {@inheritDoc}
     * @see ServerService#addClient(Client)
     */
    public void addClient(Client p_client)
    {
        super.addClient(p_client);

        sendUserList();
    }

    /**
     * {@inheritDoc}
     * @see ServerService#removeClient(Client)
     */
    public void removeClient(Client client)
    {
        super.removeClient(client);

        sendUserList();
    }

    public void sendUserList()
    {
        String users = "";

        for (Client client : clients)
            users += client.getUser().getUsername() + "\n";

        for (Client client : clients)
            send(client, new UsersContent(users));
    }
}
