package whatevergame.services.login.server;

import whatevergame.communication.Connection;

import whatevergame.server.Client;

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

    // TODO : Shouldn't need to cast.
    public void receive(Client client, whatevergame.services.Content p_content)
    {
        Content content = (Content)p_content;

        logger.debug("LoginService received content\n    [" + content + "] from client\n    [" + client + "]");

        String[] arguments;
        switch (content.getCommand())
        {
            case (Content.CMD_LOGIN):
                arguments = content.getArguments().split(":");
                if (logIn(arguments[0], arguments[1]))
                {
                    send(client, new Content(Content.CMD_LOGIN, "success"));
                    services[LOGIN].removeClient(client);
                    services[LOBBY].addClient(client);
                }
                else
                    send(client, new Content(Content.CMD_LOGIN, "fail"));
                break;

            case (Content.CMD_LOGOUT):
                send(client, new Content(Content.CMD_LOGOUT, "fail"));
                break;

            case (Content.CMD_REGISTER):
                arguments = content.getArguments().split(":");
                if (database.getUserByUsername(arguments[0]) != null)
                    send(client, new Content(Content.CMD_REGISTER, "fail:username_unavailable"));
                else
                {
                    User user = database.addUser(arguments[0], arguments[1]);
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

    protected boolean logIn(String username, String password)
    {
        //if (username.equals("admin") && password.equals("admin"))
        User user = database.getUserByUsername(username);
        if (user != null)
        {
            return true;
        }
        else
            return false;
    }
}
