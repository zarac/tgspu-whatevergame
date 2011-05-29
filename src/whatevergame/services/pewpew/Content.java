package whatevergame.services.pewpew;

@SuppressWarnings("serial")
public class Content extends whatevergame.services.Content
{
    public final static int PEWPEW_INIT = 0;
    public final static int PEWPEW_SHOOT = 1;
    public final static int PEWPEW_SPINN = 2;
    public final static int PEWPEW_EXIT = 3;
    public final static int PEWPEW_REPLAY = 4;
    public final static int NOT_YOUR_TURN = 5;
    public final static int GAME_OVER = 6;

    protected int command;
    protected int roomId;

    public Content(int command)
    {
        this.command = command;
    }

    public Content(int command, int roomId)
    {
        this.command = command;
        this.roomId = roomId;
    }

    /**
     * Gets the command for this instance.
     *
     * @return The command.
     */
    public int getCommand()
    {
        return this.command;
    }
    
    /**
     * Gets the roomId for this instance.
     *
     * @return The roomId.
     */
    public int getRoomId()
    {
        return this.roomId;
    }

    /**
     * {@inheritDoc}
     * @see Object#toString()
     */
    public String toString()
    {
        return "command=" + command;
    }
}
