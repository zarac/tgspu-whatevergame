package whatevergame.services.motd;

public class Content extends whatevergame.services.Content
{
    protected String content;

    public Content(String str)
    {
        content = str;
    }

    public String toString()
    {
        return content;
    }
}
