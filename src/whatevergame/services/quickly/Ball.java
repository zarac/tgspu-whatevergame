package whatevergame.services.quickly;

import java.awt.Color;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Ball implements Serializable
{
    public int life;
    public float x;
    public float y;
    public int size;
    public int points;
    public Color color;

    public Ball(int life, float x, float y, int size, int points, Color color)
    {
        this.life = life;
        this.x = x;
        this.y = y;
        this.size = size;
        this.points = points;
        this.color = color;
    }

    public String toString()
    {
        return "Ball: life=" + life + ", x=" + x + ", y=" + y + ", size=" + size + ", points=" + points + ", color=" + color;
    }
}
