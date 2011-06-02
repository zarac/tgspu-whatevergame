package whatevergame.services.motd.client;

import javax.swing.JOptionPane;

import whatevergame.services.ClientService;

import whatevergame.services.motd.Content;

public class Client extends ClientService
{
    public Client(int id, whatevergame.client.Client client)
    {
        super(id, client);
    }

    /**
     * {@inheritDoc}
     * @see ClientService#receive(Content)
     */
    // TODO : Shouldn't need to cast.
    public void receive(whatevergame.services.Content p_content)
    {
        Content content = (Content)p_content;
        JOptionPane.showMessageDialog(null, content.getMotd());
        logger.info(content.toString());
    }
}
