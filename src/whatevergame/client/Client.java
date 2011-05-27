package whatevergame.client;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.GridLayout;
import java.awt.Toolkit;

import java.io.IOException;

import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import logging.Logger;

import whatevergame.communication.Connection;

import whatevergame.services.ClientService;
import whatevergame.services.Service;

/**
 * The client version of Whatever Game.
 *
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
public class Client
{
    // TODO : javadoc
    protected Connection connection;
    protected ClientService[] services;
    protected String serverIp;
    protected int serverPort;
    protected ServerConnector serverConnector;
    
    protected Logger logger;

    /**
     * Creates an instance of a client.
     * TODO : Local port should not be fixed to 3000.
     * 
     * @param ip Address to connect to.
     * @param port Port to connect to.
     */
    public Client(String ip, int port)
    {
        serverIp = ip;
        serverPort = port;

        logger = new Logger(this);

        initServices();

        connection = new Connection();

        serverConnector = new ServerConnector();
    }

    /**
     * Initializes the services.
     * TODO : Should be disabled by default.
     */
    public void initServices()
    {
        services = new ClientService[Service.COUNT];
        services[Service.LOGIN] = new whatevergame.services.login.client.Client(Service.LOGIN, this);
        services[Service.CHAT] = new whatevergame.services.chat.client.Client(Service.CHAT, this);
        services[Service.PEWPEW] = new whatevergame.services.pewpew.client.Client(Service.PEWPEW, this);
        services[Service.FIVEPAD] = new whatevergame.services.fivepad.client.Client(Service.FIVEPAD, this);
        services[Service.TEST] = new whatevergame.services.test.client.Client(Service.TEST, this);
        services[Service.MOTD] = new whatevergame.services.motd.client.Client(Service.MOTD, this);
        services[Service.LOBBY] = new whatevergame.services.lobby.client.Client(Service.LOBBY, this);
        services[Service.SCORE] = new whatevergame.services.score.client.Client(Service.SCORE, this);
    }

    /**
     * Enable services.
     * Depends on being connected.
     */
    public void enableServices()
    {
        services[Service.LOGIN].enable();
    }

    /**
     * Tries to connect to the server.
     * 
     * @return If successful or not.
     */
    public boolean connect(String host, int port)
    {
        // TODO : ? Should be in another thread
        logger.debug("trying to connecting...");
        // -1 means no session ID
        try
        {
            //connection = new Connection(services, new Socket(serverIp, serverPort), -1);
            connection.init(services, new Socket(host, port), -1);
            connection.connect();
            logger.info("connected to '" + host + ":" + port + "'");
            return true;
        }
        catch (UnknownHostException e)
        {
            logger.error("Could not create socket, unknown host (" + e.getMessage() + ").");
            return false;
        }
        catch (IOException e)
        {
            logger.error("Could not create socket (" + e.getMessage() + ").");
            return false;
        }
    }

    /**
     * Gets the connection for this instance.
     *
     * @return The connection.
     */
    public Connection getConnection()
    {
        return this.connection;
    }

    /**
     * Gets the services for this instance.
     *
     * @return The services.
     */
    public ClientService[] getServices()
    {
        return this.services;
    }

    /**
     * Gets the services for this instance.
     *
     * @param index The index to get.
     * @return The services.
     */
    public ClientService getService(int index)
    {
        return this.services[index];
    }

    protected class ServerConnector extends JFrame
    {
        protected Host host;
        protected Port port;
        protected Connect connect;
        protected Feedback feedback;

        protected ServerConnector()
        {
            //setUndecorated(true);
            setVisible(true);
            // http://java.sun.com/developer/technicalArticles/GUI/translucent_shaped_windows/#Enabling-Per-Pixel-Translucency
            //AWTUtilities.setWindowOpacity(this, 0.5f);
            //AWTUtilities.setWindowOpaque(this, false);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            setBounds(dimension.width/4, dimension.height/4, dimension.width/2, dimension.height/2);
            setLayout(new GridLayout(4,1));

            host = new Host();
            port = new Port();
            connect = new Connect();
            feedback = new Feedback("Please connect");

            add(feedback);
            add(host);
            add(port);
            add(connect);

            // TODO : This shouldn't be needed, but java is inconsistant which
            // randomly causes a blank frame. Perhaps this will prevent it.
            feedback.revalidate();
            host.revalidate();
            port.revalidate();
            connect.revalidate();
        }

        protected class Host extends JTextField
        {
            public Host()
            {
                setHorizontalAlignment(CENTER);
                setText(serverIp);
            }
        }

        protected class Port extends JTextField
        {
            public Port()
            {
                setHorizontalAlignment(CENTER);
                setText(""+serverPort);
            }
        }

        protected class Connect extends JButton implements ActionListener
        {
            public Connect()
            {
                setText("Connect");
                addActionListener(this);
            }

            /**
             * {@inheritDoc}
             * @see ActionListener#actionPerformed(ActionEvent)
             */
            public void actionPerformed(ActionEvent e)
            {
                if (connect(host.getText(), Integer.parseInt(port.getText())))
                {
                    feedback.set("Connected! =D", Feedback.SUCCESS);
                    //ServerConnector.this.setVisible(false);
                    ServerConnector.this.dispose();
                    enableServices();
                }
                else
                {
                    feedback.set("Unable to connect!", Feedback.ERROR);
                }
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
}
