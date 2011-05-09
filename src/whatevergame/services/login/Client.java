package whatevergame.services.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import whatevergame.communication.WrappedPackage;

import whatevergame.Service;
import whatevergame.Package;

public class Client extends Service
{
    protected Gui gui;

    public Client()
    {
        gui = new Gui();
    }

    /**
     * Gets the gui for this instance.
     *
     * @return The gui.
     */
    public Gui getGui()
    {
        return this.gui;
    }

    /**
     * {@inheritDoc}
     * @see Runnable#run()
     */
    public void run()
    {
    }

    public void logIn(String username, String password)
    {
        sendPackage(new Package(new Content(username, password), serviceId, NO_SESSION_ID));
    }

    /**
     * {@inheritDoc}
     * @see Service#sendPackage(Package)
     */
    public void sendPackage(Package p_package)
    {
        communicator.send(new WrappedPackage(p_package, sessions.getFirst().getConnection()));
    }

    /**
     * {@inheritDoc}
     * @see Service#recievePackage(Package)
     */
    public void recievePackage(Package p_package)
    {
    }

    @SuppressWarnings("serial")
    protected class Gui extends JPanel
    {
        protected Feedback feedback;
        protected Username username;
        protected Password password;
        protected Submit submit;

        public Gui()
        {
            setLayout(new GridLayout(4,1));
            feedback = new Feedback();
            add(feedback);
            username = new Username();
            add(username);
            password = new Password();
            add(password);
            submit = new Submit();
            add(submit);
        }

        protected class Feedback extends JTextField
        {
            public Feedback()
            {
                setText("Log in please...");
            }
        }

        protected class Username extends JTextField
        {
            public Username()
            {
                setText("Username");
            }
        }

        protected class Password extends JPasswordField
        {
            public Password()
            {
                setText("Password");
            }
        }
        
        protected class Submit extends JButton implements ActionListener
        {
            public Submit()
            {
                setText("Login");
                addActionListener(this);
            }

            /**
             * {@inheritDoc}
             * @see ActionListener#actionPerformed(ActionEvent)
             */
            public void actionPerformed(ActionEvent e)
            {
                //feedback.setText("You entered username='" + username.getText() + "', password='" + new String(password.getPassword()) + "'");
                feedback.setText("Hold on to something while we're trying to log you in...");
            }
        }
    }
}
