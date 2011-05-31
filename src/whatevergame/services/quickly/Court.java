package whatevergame.services.quickly;

import java.io.Serializable;

import java.util.LinkedList;

public class Court implements Serializable
{
    public int width;
    public int height;
    protected LinkedList<Ball> balls;

    /**
     * Constructs a new instance.
     */
    public Court(int width, int height)
    {
        this.width = width;
        this.height = height;
        balls = new LinkedList<Ball>();
    }

    public void addBall(Ball ball)
    {
        balls.add(ball);
    }

    /**
     * Gets the balls for this instance.
     *
     * @return The balls.
     */
    public LinkedList<Ball> getBalls()
    {
        return this.balls;
    }
}
