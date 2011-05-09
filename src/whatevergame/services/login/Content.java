package whatevergame.services.login;

public class Content extends whatevergame.Content
{
    protected String username;
    protected String password;

    public Content(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
}

