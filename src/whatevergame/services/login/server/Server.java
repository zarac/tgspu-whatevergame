package whatevergame.services.login.server;

import whatevergame.communication.Connection;

import whatevergame.server.Client;
import whatevergame.server.User;

import whatevergame.services.ServerService;

import whatevergame.services.login.Content;

/**
 * Takes care of clients who want to log in in order to gain privileges.
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
public class Server extends ServerService
{
    protected Database database;

    /**
     * @see ServerService#ServerService(int,Connection)
     */
    public Server(int id, whatevergame.server.Server server)
    {
        super(id, server);

        // database connection
        database = new Database();
        database.dumpUserTable();
    }

    public void receive(Client client, whatevergame.services.Content p_content)
    {
        Content content = (Content)p_content;

        logger.debug("LoginService received content\n    [" + content + "] from client\n    [" + client + "]");

        User user;
        String[] argument;
        switch (content.getCommand())
        {
            case (Content.CMD_LOGIN):
                argument = content.getArgument().split(":");
                user = logIn(argument[0], argument[1]);
                if (user == null)
                    send(client, new Content(Content.CMD_LOGIN, "fail"));
                else
                {
                    client.setUser(user);
                    send(client, new Content(Content.CMD_LOGIN, "success"));
                    getService(LOGIN).removeClient(client);
                    getService(LOBBY).addClient(client);
                }
                break;

            case (Content.CMD_LOGOUT):
                send(client, new Content(Content.CMD_LOGOUT, "fail"));
                break;

            case (Content.CMD_REGISTER):
                argument = content.getArgument().split(":");
                if (database.getUserByUsername(argument[0]) != null)
                    send(client, new Content(Content.CMD_REGISTER, "fail:username_unavailable"));
                else
                {
                    user = database.addUser(argument[0], argument[1]);
                    if (user == null)
                        send(client, new Content(Content.CMD_REGISTER, "fail:unknown"));
                    else
                        send(client, new Content(Content.CMD_REGISTER, "success"));
                }
                break;

            default:
                logger.warning("unknown command");
        }
    }

    /**
     * {@inheritDoc}
     * @see ServerService#addClient(Client)
     */
    public void addClient(Client client)
    {
        super.addClient(client);
        logger.info("client added\n    [" + client + "]");
        send(client, new Content(Content.CMD_LOGIN, "Welcome!"));
    }

    protected User logIn(String username, String password)
    {
        return database.getUserByUsername(username);
    }
}
