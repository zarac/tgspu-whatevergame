package whatevergame.services.login.server;

import logging.Logger;

import whatevergame.communication.Connection;

import whatevergame.server.Client;

import whatevergame.services.ServerService;

import whatevergame.services.login.Content;
import whatevergame.services.Package;

public class Server extends ServerService
{
    /**
     * A logger, it's handy to have.
     */
    protected Logger logger;

    /**
     * @see ServerService#ServerService(int,Connection)
     */
    public Server(int id)
    {
        super(id);

        logger = new Logger(this);
    }

    /**
     * {@inheritDoc}
     * @see whatevergame.services.Service#receivePackage(Package)
     */
    public void receivePackage(Package p_package)
    {
        logger.debug("LoginService received package\n    [" + p_package + "]");

        Content content = (Content)p_package.getContent();
        switch (content.getCommand())
        {
            case (Content.CMD_LOGIN):
                logger.debug("trying to log in, huh?");
                break;
            case (Content.CMD_LOGOUT):
                logger.debug("trying to log out, huh?");
                break;
            case (Content.CMD_REGISTER):
                logger.debug("trying to register, huh?");
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
        client.send(new Package(new Content(Content.CMD_LOGIN, "Wellcome!"), id));
    }
}
