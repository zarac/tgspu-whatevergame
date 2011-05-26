package whatevergame.services.lobby.client;

import whatevergame.services.ClientService;

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

    // TODO : Shouldn't need to cast.
    public void receive(whatevergame.services.Content p_content)
    {
        Content content = (Content)p_content;

        logger.debug("receive(" + content + "):");
    }
}
