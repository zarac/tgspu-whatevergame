package whatevergame.services.login.client;

import whatevergame.services.ClientService;

import whatevergame.services.login.Content;

/**
 * The login client service.
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
public class Client extends ClientService
{
    protected Gui gui;

    /**
     * @see ClientService#ClientService(int,Client)
     */
    public Client(int id, whatevergame.client.Client client)
    {
        super(id, client);

        gui = new Gui(this);
    }

    // TODO : Shouldn't need to cast.
    public void receive(whatevergame.services.Content p_content)
    {
        Content content = (whatevergame.services.login.Content)p_content;

        logger.debug("receive(" + content + "):");

        switch (content.getCommand())
        {
            case (Content.CMD_LOGIN):
                logger.debug("did i log in?");
                break;
            case (Content.CMD_LOGOUT):
                logger.debug("did i log out?");
                break;
            case (Content.CMD_REGISTER):
                logger.debug("did i register?");
                break;
            default:
                logger.warning("unknown command");
        }
    }

    public void logIn(String username, String password)
    {
        // TODO : ? LoginContent(user, pass)
        send(new Content(Content.CMD_LOGIN, username + ":" + password));
    }

    public void register(String username, String password)
    {
        // TODO : ? RegisterContent(user, pass)
        send(new Content(Content.CMD_REGISTER, username + ":" + password));
    }
}
