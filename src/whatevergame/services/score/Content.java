package whatevergame.services.score;

public class Content extends whatevergame.services.Content
{
    protected String test;

    public Content(String test)
    {
        this.test = test;
    }

    /**
     * {@inheritDoc}
     * @see Object#toString()
     */
    public String toString()
    {
        return test;
    }
}
