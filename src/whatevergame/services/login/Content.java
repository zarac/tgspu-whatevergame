package whatevergame.services.login;

public class Content extends whatevergame.services.Content
{
    public final static int CMD_LOGIN = 1;
    public final static int CMD_LOGOUT = 2;
    public final static int CMD_REGISTER = 3;

    protected int command;
    // TODO : ? extend this Content and cast depending on command
    protected String arguments;

    public Content(int command, String arguments)
    {
        this.command = command;
        this.arguments = arguments;
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
     * Gets the arguments for this instance.
     *
     * @return The arguments.
     */
    public String getArguments()
    {
        return this.arguments;
    }
}
