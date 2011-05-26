package whatevergame.services.chat;

public class Content extends whatevergame.services.Content
{
    protected String test;

    /**
     * Creates an instance of Content.
     * 
     * @param test The test string
     */
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
