package whatevergame.services.chat;

public class Content extends whatevergame.services.Content
{
    protected String message;

    /**
     * Creates an instance of Content.
     * 
     * @param message The message
     */
    public Content(String message)
    {
        this.message = message;
    }

    /**
     * {@inheritDoc}
     * @see Object#toString()
     */
    public String toString()
    {
        return message;
    }

    /**
     * Gets the message for this instance.
     *
     * @return The message.
     */
    public String getMessage()
    {
        return this.message;
    }
}
