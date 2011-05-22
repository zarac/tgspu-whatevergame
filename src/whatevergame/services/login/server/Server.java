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
    /**
     * @see ServerService#ServerService(int,Connection)
     */
    public Server(int id)
    {
        super(id);
    }

    // TODO : Shouldn't need to cast.
    public void receive(Client client, whatevergame.services.Content p_content)
    {
        Content content = (Content)p_content;
        logger.debug("LoginService received content\n    [" + content + "]\nfrom client\n    [" + client + "]");

        switch (content.getCommand())
        {
            case (Content.CMD_LOGIN):
                send(client, new Content(Content.CMD_LOGIN, "trying to log in, huh?"));
                break;
            case (Content.CMD_LOGOUT):
                send(client, new Content(Content.CMD_LOGOUT, "trying to log out, huh?"));
                break;
            case (Content.CMD_REGISTER):
                send(client, new Content(Content.CMD_REGISTER, "trying to register, huh?"));
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
        send(client, new Content(Content.CMD_LOGIN, "Wellcome!"));
    }

    protected boolean logIn(String username, String password)
    {
        if (username.equals("admin") && password.equals("admin"))
        {
            return true;
        }
        else
            return false;
    }
}
