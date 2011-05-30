package whatevergame.services.fivepad.client;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import whatevergame.services.ClientService;

import whatevergame.services.fivepad.Content;

public class Client extends ClientService {

    FivePadGui gui;

    /**
     * {@inheritDoc}
     * @see ClientService#Client(int,Client)
     */
    public Client(int id, whatevergame.client.Client client) {
        super(id, client);

        gui = new FivePadGui(this);

    }

    public FivePadGui getGui() {
        return gui;
    }

    /**
     * {@inheritDoc}
     * @see ClientService#receive(Content)
     */
    // TODO : Shouldn't need to cast.
    public void receive(whatevergame.services.Content p_content) {

        Content content = (Content) p_content;
        System.out.println("content: " + content.toString());
        logger.info(content.toString());
        if (content.toString().equalsIgnoreCase("your turn")){
            gui.setLabelText(content.toString());
        }
//        content.
//        gui.setLabelText(content.toString());
    }
//    public JPanel getGui()
//    {
//        JPanel panel = new JPanel();
//        panel.setLayout(new GridLayout(1,1));
//        panel.add(new JLabel("GIVE PAD GUI"));
//        return panel;
//    }
}
