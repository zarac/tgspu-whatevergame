package whatevergame.services.lobby;

public class Content extends whatevergame.services.Content
{
    protected String command;

    public Content(String command)
    {
    }

    /**
     * Gets the command for this instance.
     *
     * @return The command.
     */
    public String getCommand()
    {
        return this.command;
    }
}
