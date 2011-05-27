package whatevergame.services.fivepad.client;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import whatevergame.services.ClientService;

import whatevergame.services.fivepad.Content;

public class Client extends ClientService
{
    /**
     * {@inheritDoc}
     * @see ClientService#Client(int,Client)
     */
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
        logger.info(content.toString());
    }

    public JPanel getGui()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,1));
        panel.add(new JLabel("GIVE PAD GUI"));
        return panel;
    }
}
