package whatevergame.server;

public class User
{
    protected String username;
    protected String password;

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the username for this instance.
     *
     * @return The username.
     */
    public String getUsername()
    {
        return this.username;
    }
}
