package whatevergame.services.login.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;

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
    protected Dimension frameDimension;


    public Gui(Client client)
    {
        this.client = client;

        setTitle("Whatever Game - Login");
        //setUndecorated(true);
        setVisible(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frameDimension = new Dimension(dimension.width / 2, dimension.height / 2);
        setBounds(dimension.width / 4, dimension.height / 4, dimension.width / 2, dimension.height / 2);

        setBackground(Color.white);
        username = new Username();
        password = new Password();
        logIn = new LogInButton();
        register = new RegisterButton();
        feedback = new Feedback("Welcome, please log in!");
        container = new Container("data/images/eightBall.png");
        add(container, BorderLayout.CENTER);
        container.revalidate();
    }

    public void setFeedback(String p_feedback)
    {
        feedback.set(p_feedback);
    }
    
    protected class Container extends JPanel
    {
        protected Image image;

        public Container(String img)
        {
            this(new ImageIcon(img).getImage());
        }

        public Container(Image img)
        {
            //setLayout(new GridLayout(5,1));
            image = img;
            // TODO : Why is this here? (diffing)
            frameDimension = Gui.this.getSize();

            setPreferredSize(frameDimension);
            setMinimumSize(frameDimension);
            setMaximumSize(frameDimension);
            setSize(frameDimension);
            setLayout(null);

            //add(feedback);
            add(username);
            add(password);
            add(logIn);
            add(register);
        }

        public void paintComponent(Graphics g)
        {
            frameDimension = Gui.this.getSize();
            g.drawImage(image, frameDimension.width / 2 - (image.getWidth(null) / 2), frameDimension.height / 2 - (image.getHeight(null) / 2), null);
            username.setBounds(frameDimension.width / 3 + 43, frameDimension.height / 4, frameDimension.width / 5, 25);
            password.setBounds(frameDimension.width / 3 + 43, frameDimension.height / 4 + 35, frameDimension.width / 5, 25);
            logIn.setBounds(frameDimension.width / 3 + 43, frameDimension.height / 4 + 125, frameDimension.width / 5, 25);
            register.setBounds(frameDimension.width / 3 + 43, frameDimension.height / 4 + 160, frameDimension.width / 5, 25);
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

    protected class Username extends JTextField implements MouseListener, KeyListener
    {
        public Username()
        {
            setHorizontalAlignment(CENTER);
            setText(DEFAULT_USERNAME);
            addMouseListener(this);
            addKeyListener(this);
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

        @Override
        public void keyTyped(KeyEvent e)
        {
//            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void keyPressed(KeyEvent e)
        {
//            throw new UnsupportedOperationException("Not supported yet.");
            if (username.getText().equals(DEFAULT_USERNAME))
            {
                setText("");
            }

        }

        @Override
        public void keyReleased(KeyEvent e)
        {
//            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    protected class Password extends JTextField implements MouseListener, KeyListener
    {
        public Password()
        {
            setHorizontalAlignment(CENTER);
            setText(DEFAULT_PASSWORD);
            addMouseListener(this);
            addKeyListener(this);
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

        @Override
        public void keyTyped(KeyEvent e)
        {
            // pass
        }

        @Override
        public void keyPressed(KeyEvent e)
        {
            if (password.getText().equals(DEFAULT_PASSWORD))
                password.setText("");
        }

        @Override
        public void keyReleased(KeyEvent e)
        {
            // pass
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
