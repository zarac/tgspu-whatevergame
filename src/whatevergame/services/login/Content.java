package whatevergame.services.login;

public class Content extends whatevergame.services.Content
{
    public final static int CMD_LOGIN = 1;
    public final static int CMD_LOGOUT = 2;
    public final static int CMD_REGISTER = 3;

    protected int command;
    // TODO : ? extend this Content and cast depending on command
    public String argument;

    public Content(int command, String argument)
    {
        this.command = command;
        this.argument = argument;
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
     * Gets the argument for this instance.
     *
     * @return The argument.
     */
    public String getArgument()
    {
        return this.argument;
    }

    public String toString()
    {
        return "command='" + command + "', argument='" + argument + "'";
    }
}
