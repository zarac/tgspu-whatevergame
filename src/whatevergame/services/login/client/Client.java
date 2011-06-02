package whatevergame.services.login.client;

import javax.swing.JOptionPane;

import whatevergame.services.ClientService;

import whatevergame.services.login.Content;

import whatevergame.services.ServiceProvider;

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

    /**
     * {@inheritDoc}
     * @see ClientService#enable()
     */
    public void enable()
    {
        gui.setVisible(true);
    }

    /**
     * {@inheritDoc}
     * @see ClientService#disable()
     */
    public void disable()
    {
        gui.setVisible(false);
    }

    // TODO : Shouldn't need to cast.
    public void receive(whatevergame.services.Content p_content)
    {
        Content content = (whatevergame.services.login.Content)p_content;

        logger.debug("receive(" + content + "):");

        switch (content.getCommand())
        {
            case (Content.CMD_LOGIN):
                if (content.argument.equals("success"))
                {
                    ServiceProvider services = client.getServices();
                    logger.debug("logged in successfully");
                    ((ClientService)services.get(LOGIN)).disable();
                    ((ClientService)services.get(LOBBY)).enable();
                }
                else
                    JOptionPane.showMessageDialog(null, "Failed to log in! Please try again.");
                break;
            case (Content.CMD_LOGOUT):
                logger.debug("did i log out?");
                break;
            case (Content.CMD_REGISTER):
                if (content.getArgument().split(":")[0].equals("fail"))
                    JOptionPane.showMessageDialog(null, "Failed to register!");
                else
                    JOptionPane.showMessageDialog(null, "Congrats! Please log in!");
                //logger.debug("did i register?");
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

    public void logOut()
    {
        // TODO : ? LoginContent(user, pass)
        send(new Content(Content.CMD_LOGOUT, null));
    }

    public void register(String username, String password)
    {
        // TODO : ? RegisterContent(user, pass)
        send(new Content(Content.CMD_REGISTER, username + ":" + password));
    }
}
