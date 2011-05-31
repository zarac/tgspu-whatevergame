package whatevergame.services.quickly.client;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.geom.Ellipse2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import whatevergame.services.ClientService;

import whatevergame.services.quickly.Ball;
import whatevergame.services.quickly.Content;
import whatevergame.services.quickly.Court;

/**
 * The quickly game client service. Hit targets quicker than your opponents!
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
@SuppressWarnings("serial")
public class Client extends ClientService
{
    protected Court court;
    protected Input input;
    protected Painter painter;

    // TODO : to server
    protected Random random;

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
     * @see ClientService#enable()
     */
    public void enable()
    {
        init();
    }

    public void init()
    {
        random = new Random();
        court = new Court(400, 300);
        court.addBall(getNormalBall(court));
        input = new Input();
        painter = new Painter(court.width, court.height);
    }

    // TODO : to server
    public Ball getNormalBall(Court court)
    {
        return new Ball(100, random.nextFloat(), random.nextFloat(), 50, 10,
                Color.RED);
    }

    public void receive(whatevergame.services.Content content)
    {
        logger.debug("Received content\n    [" +
                (whatevergame.services.quickly.Content)content + "]");
        send(new Content("Hello right back at ya! ;)"));
    }

    public class Gui extends JPanel
    {
    }

    protected class Input implements MouseListener
    {
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
            court.addBall(getNormalBall(court));
            painter.repaint();
            logger.debug("mouseReleased(): added ball!");
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
            // pass
        }

        /**
         * {@inheritDoc}
         * @see MouseListener#mouseExited(MouseEvent)
         */
        public void mouseExited(MouseEvent e)
        {
            // pass
        }
    }

    protected class Painter extends JPanel
    {
        protected JFrame frame;
        protected JPanel panel;
        protected Graphics graphics;
        protected int width, height;

        /**
         * Constructs a new instance.
         */
        public Painter(int width, int height)
        {
            //setLayout(null);
            //setBounds(100, 100, width, height);
            addMouseListener(input);
            setBackground(Color.BLACK);

            //graphics = panel.getGraphics();

            frame = new JFrame();
            frame.setVisible(true);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.setBounds(100, 100, width, height);
            frame.add(this, BorderLayout.CENTER);
        }

        public void draw(Graphics p_g)
        {
            Graphics2D g = (Graphics2D)p_g;
            int x, y;
            logger.debug("draw():");
            for (Ball ball : court.getBalls())
            {
                x = (int)(ball.x*(float)court.width);
                y = (int)(ball.y*(float)court.height);
                logger.debug("draw(): ball=" + ball + ", x=" + x + ", y=" + y);
                g.setColor(ball.color);
                g.fillOval(x, y, ball.size, ball.size);
            }
        }

        /**
         * {@inheritDoc}
         * @see javax.swing.JComponent#paintComponent(Graphics)
         */
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            logger.debug("paintComponent():");
            draw(g);
        }
    }
}
