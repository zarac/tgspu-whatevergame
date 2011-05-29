package whatevergame.services.pewpew;

public class StateContent extends whatevergame.services.Content
{
    protected State state;

    public StateContent(State state)
    {
        this.state = state;
    }

    /**
     * Gets the state for this instance.
     *
     * @return The state.
     */
    public State getState()
    {
        return this.state;
    }
}
