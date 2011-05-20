package whatevergame.services.test;

/**
 * The content of packages used to communicate between the test server and
 * clients. All it contains is a test string.
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
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
