package whatevergame.services.score;

public class Content extends whatevergame.services.Content
{
    protected String score;

    public Content(String score)
    {
        this.score = score;
    }

    /**
     * {@inheritDoc}
     * @see Object#toString()
     */
    public String toString()
    {
        return score;
    }

    /**
     * Gets the score for this instance.
     *
     * @return The score.
     */
    public String getScore()
    {
        return this.score;
    }
}
