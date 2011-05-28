package whatevergame.services.lobby.client;

import whatevergame.services.ClientService;
import whatevergame.services.ExitContent;

import whatevergame.services.lobby.Content;

/**
 * The lobby client service.
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

    public whatevergame.services.chat.client.Client getChat()
    {
        return (whatevergame.services.chat.client.Client)client.getService(CHAT);
    }

    public whatevergame.services.score.client.Client getScore()
    {
        return (whatevergame.services.score.client.Client)client.getService(SCORE);
    }

    public whatevergame.services.fivepad.client.Client getFivePad()
    {
        return (whatevergame.services.fivepad.client.Client)client.getService(FIVEPAD);
    }

    public whatevergame.services.pewpew.client.Client getPewPew()
    {
        return (whatevergame.services.pewpew.client.Client)client.getService(PEWPEW);
    }

    /**
     * {@inheritDoc}
     * @see ClientService#enable()
     */
    public void enable()
    {
        gui.init();
        gui.setVisible(true);
    }

    /**
     * {@inheritDoc}
     * @see ClientService#disable()
     */
    public void disable()
    {
        gui.setVisible(false);
        gui.dispose();

        client.getService(CHAT).disable();
        client.getService(SCORE).disable();
        client.getService(PEWPEW).disable();
        client.getService(FIVEPAD).disable();

        whatevergame.services.login.client.Client login = (whatevergame.services.login.client.Client)client.getService(LOGIN);
        login.logOut();
        login.enable();

        send(new ExitContent());
    }

    public void receive(whatevergame.services.Content p_content)
    {
        Content content = (Content)p_content;

        logger.debug("receive(" + content + "):");
    }

    public Gui.BackToGameSelect getBackToGameSelectButton()
    {
        return gui.getBackToGameSelect();
    }
}
