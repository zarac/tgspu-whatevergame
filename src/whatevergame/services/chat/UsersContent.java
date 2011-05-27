package whatevergame.services.chat;

public class UsersContent extends whatevergame.services.Content
{
    protected String users;

    /**
     * Creates an instance of UsersContent.
     * 
     * @param users The users
     */
    public UsersContent(String users)
    {
        this.users = users;
    }

    /**
     * Gets the users for this instance.
     *
     * @return The users.
     */
    public String getUsers()
    {
        return this.users;
    }
}
