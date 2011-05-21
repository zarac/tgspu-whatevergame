package whatevergame.services.login.client;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Gui extends JFrame
{
    protected String DEFAULT_USERNAME = "<username>";
    protected String DEFAULT_PASSWORD = "<password>";

    protected Client client;

    protected Container container;
    protected Username username;
    protected Password password;
    protected LogInButton logIn;
    protected RegisterButton register;


    public Gui(Client client)
    {
        this.client = client;

        setTitle("Authentication Plz Kthx");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBounds(100, 100, 300, 300);

        username = new Username();
        password = new Password();
        logIn = new LogInButton();
        register = new RegisterButton();
        container = new Container();
        add(container, BorderLayout.CENTER);

        container.revalidate();
    }
    
    protected class Container extends JPanel
    {
        public Container()
        {
            setLayout(new GridLayout(5,1));
            setBackground(new Color(140, 230, 42));
            setBackground(new Color(140, 230, 42));

            add(new JLabel("Hi there, welcome!"));
            add(username);
            add(password);
            add(logIn);
            add(register);
        }
    }

    protected class LogInButton extends JButton implements ActionListener
    {
        public LogInButton()
        {
            setText("Log In");
            addActionListener(this);
        }

        /**
         * {@inheritDoc}
         * @see ActionListener#actionPerformed(ActionEvent)
         */
        public void actionPerformed(ActionEvent e)
        {
            client.logIn(username.getText(), password.getText());
        }
    }

    protected class RegisterButton extends JButton implements ActionListener
    {
        public RegisterButton()
        {
            setText("Register");
            addActionListener(this);
        }

        /**
         * {@inheritDoc}
         * @see ActionListener#actionPerformed(ActionEvent)
         */
        public void actionPerformed(ActionEvent e)
        {
            client.register(username.getText(), password.getText());
        }
    }

    protected class Username extends JTextField implements MouseListener
    {
        public Username()
        {
            setText(DEFAULT_USERNAME);
            setOpaque(false);
            addMouseListener(this);
        }

        /**
         * {@inheritDoc}
         * @see MouseListener#mouseClicked(MouseEvent)
         */
        public void mouseClicked(MouseEvent e)
        {
            // pass
        }

        /**
         * {@inheritDoc}
         * @see MouseListener#mousePressed(MouseEvent)
         */
        public void mousePressed(MouseEvent e)
        {
            // pass
        }

        /**
         * {@inheritDoc}
         * @see MouseListener#mouseReleased(MouseEvent)
         */
        public void mouseReleased(MouseEvent e)
        {
        }

        /**
         * {@inheritDoc}
         * @see MouseListener#mouseEntered(MouseEvent)
         */
        public void mouseEntered(MouseEvent e)
        {
            if (username.getText().equals(DEFAULT_USERNAME))
                setText("");
        }

        /**
         * {@inheritDoc}
         * @see MouseListener#mouseExited(MouseEvent)
         */
        public void mouseExited(MouseEvent e)
        {
            if (username.getText().equals(""))
                setText(DEFAULT_USERNAME);
        }
    }

    protected class Password extends JTextField implements MouseListener
    {
        public Password()
        {
            setText(DEFAULT_PASSWORD);
            setOpaque(false);
            addMouseListener(this);
        }

        /**
         * {@inheritDoc}
         * @see MouseListener#mouseClicked(MouseEvent)
         */
        public void mouseClicked(MouseEvent e)
        {
            // pass
        }

        /**
         * {@inheritDoc}
         * @see MouseListener#mousePressed(MouseEvent)
         */
        public void mousePressed(MouseEvent e)
        {
            // pass
        }

        /**
         * {@inheritDoc}
         * @see MouseListener#mouseReleased(MouseEvent)
         */
        public void mouseReleased(MouseEvent e)
        {
        }

        /**
         * {@inheritDoc}
         * @see MouseListener#mouseEntered(MouseEvent)
         */
        public void mouseEntered(MouseEvent e)
        {
            if (password.getText().equals(DEFAULT_PASSWORD))
                setText("");
        }

        /**
         * {@inheritDoc}
         * @see MouseListener#mouseExited(MouseEvent)
         */
        public void mouseExited(MouseEvent e)
        {
            if (password.getText().equals(""))
                setText(DEFAULT_PASSWORD);
        }
    }
}
