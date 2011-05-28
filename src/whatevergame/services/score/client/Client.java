package whatevergame.services.score.client;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import whatevergame.services.ClientService;

import whatevergame.services.score.Content;

public class Client extends ClientService
{
    protected Gui gui;

    public Client(int id, whatevergame.client.Client client)
    {
        super(id, client);

        gui = new Gui();
    }

    // TODO : Shouldn't need to cast
    public void receive(whatevergame.services.Content p_content)
    {
        if (p_content instanceof Content)
        {
            Content content = (Content)p_content;
            gui.setScore(content.getScore());
        }
    }

    public JPanel getGui()
    {
        return gui;
    }

    protected class Gui extends JPanel
    {
        protected JTextArea score;

        public Gui()
        {
            setLayout(new BorderLayout());
            score = new JTextArea();
            add(new JScrollPane(score), BorderLayout.CENTER);
        }

        public void setScore(String score)
        {
            this.score.setText("High Score\n" + score);
        }
    }
}
