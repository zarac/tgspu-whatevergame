package whatevergame.services.chat.client;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.GridLayout;

import java.util.EventListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import whatevergame.services.chat.UsersContent;

import whatevergame.services.ClientService;

import whatevergame.services.chat.Content;

public class Client extends ClientService
{
    protected Gui gui;

    /**
     * {@inheritDoc}
     * @see ClientService#Client(int,Client)
     */
    public Client(int id, whatevergame.client.Client client)
    {
        super(id, client);

        gui = new Gui();
    }

    // TODO : Shouldn't need to cast
    public void receive(whatevergame.services.Content p_content)
    {
        if (p_content instanceof UsersContent)
        {
            logger.debug("received user list\n    [" + p_content + "]");
            gui.setUsers(((UsersContent)p_content).getUsers());
        }
        else if (p_content instanceof Content)
        {
            Content content = (Content)p_content;
            logger.debug("Received content\n    [" + content + "]");
            gui.append(content.getMessage() + "\n");
        }
    }

    public JPanel getGui()
    {
        return gui;
    }

    protected class Gui extends JPanel implements ActionListener, EventListener
    {
        protected JTextArea users;
        protected JTextArea log;
        protected JTextField input;
        protected JPanel inputPanel;
        protected JButton send;

        protected Gui()
        {
            add(new JLabel("Chat GUI"));

            users = new JTextArea();
            log = new JTextArea();
            input = new JTextField();
            inputPanel = new JPanel();
            send = new JButton("Send");

            input.addActionListener(this);
            send.addActionListener(this);

            setLayout(new BorderLayout());
            inputPanel.setLayout(new BorderLayout());
            add(new JScrollPane(log), BorderLayout.CENTER);
            inputPanel.add(input, BorderLayout.CENTER);
            inputPanel.add(send, BorderLayout.EAST);
            add(inputPanel, BorderLayout.SOUTH);
            add(new JScrollPane(users), BorderLayout.EAST);
        }

        /**
         * {@inheritDoc}
         * @see ActionListener#actionPerformed(ActionEvent)
         */
        public void actionPerformed(ActionEvent e)
        {
            send(new Content(input.getText()));
            input.setText("");
        }

        public void append(String message)
        {
            log.append(message);
        }

        public void setUsers(String p_users)
        {
            users.setText(p_users);
            users.revalidate();
            revalidate();
        }
    }
}
