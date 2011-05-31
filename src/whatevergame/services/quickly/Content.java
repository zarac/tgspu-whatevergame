package whatevergame.services.quickly;

/**
 * The content of packages used to communicate between the quickly server and
 * clients. All it contains is a quickly string.
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
public class Content extends whatevergame.services.Content
{
    protected String quickly;

    /**
     * Creates an instance of Content.
     * 
     * @param quickly The quickly string
     */
    public Content(String quickly)
    {
        this.quickly = quickly;
    }

    /**
     * {@inheritDoc}
     * @see Object#toString()
     */
    public String toString()
    {
        return quickly;
    }
}
