package whatevergame.services.motd;

public class Content extends whatevergame.services.Content
{
    protected String motd;

    public Content(String motd)
    {
        this.motd = motd;
    }

    public String toString()
    {
        return motd;
    }

    /**
     * Gets the motd for this instance.
     *
     * @return The motd.
     */
    public String getMotd()
    {
        return this.motd;
    }
}
