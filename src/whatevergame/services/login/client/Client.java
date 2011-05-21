package whatevergame.services.login.client;

import logging.Logger;

import whatevergame.communication.Connection;

import whatevergame.services.ClientService;

import whatevergame.services.login.Content;
import whatevergame.services.Package;

public class Client extends ClientService
{
    protected Gui gui;

    /**
     * A logger, it's handy to have.
     */
    protected Logger logger;

    /**
     * @see ClientService#ClientService(int,Connection)
     */
    public Client(int id, Connection connection)
    {
        super(id, connection);

        logger = new Logger(this);

        gui = new Gui(this);
    }

    /**
     * {@inheritDoc}
     * @see whatevergame.services.Service#receivePackage(Package)
     */
    public void receivePackage(Package p_package)
    {
        Content content = (Content)p_package.getContent();
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
        send(new Package(new Content(Content.CMD_LOGIN, username + ":" + password), id));
    }

    public void register(String username, String password)
    {
        // TODO : ? RegisterContent(user, pass)
        send(new Package(new Content(Content.CMD_REGISTER, username + ":" + password), id));
    }
}
