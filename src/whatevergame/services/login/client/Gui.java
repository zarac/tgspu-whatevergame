package whatevergame.services.login.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.GridLayout;
import java.awt.Toolkit;

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
    protected Feedback feedback;


    public Gui(Client client)
    {
        this.client = client;

        setTitle("Whatever Game - Login");
        //setUndecorated(true);
        setVisible(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(dimension.width/4, dimension.height/4, dimension.width/2, dimension.height/2);

        username = new Username();
        password = new Password();
        logIn = new LogInButton();
        register = new RegisterButton();
        feedback = new Feedback("Welcome, please log in!");
        container = new Container();
        add(container, BorderLayout.CENTER);
        container.revalidate();
    }
    
    protected class Container extends JPanel
    {
        public Container()
        {
            setLayout(new GridLayout(5,1));

            add(feedback);
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
            setHorizontalAlignment(CENTER);
            setText(DEFAULT_USERNAME);
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
            // pass
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
            setHorizontalAlignment(CENTER);
            setText(DEFAULT_PASSWORD);
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

    protected class Feedback extends JLabel
    {
        public final static int NORMAL = 0;
        public final static int ERROR = 1;
        public final static int SUCCESS = 2;

        public Feedback(String text)
        {
            setHorizontalAlignment(CENTER);
            setText(text);
        }

        public void set(String text)
        {
            set(text, 0);
        }

        public void set(String text, int type)
        {
            if (type == NORMAL)
                setForeground(Color.BLACK);
            else if (type == ERROR)
                setForeground(Color.RED);
            else if (type == SUCCESS)
                setForeground(Color.GREEN);

            setText(text);
        }

        public void add(String text)
        {
            setText(getText() + "\n" + text);
        }

        public void clear()
        {
            setText("");
        }
    }
}
