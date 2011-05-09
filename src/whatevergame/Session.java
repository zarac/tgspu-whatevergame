package whatevergame;

import whatevergame.communication.Connection;

public class Session
{
    protected Connection connection;
    // -1 represents no ID
    protected int id;

    /**
     *  Creates a new session.
     * 
     * @param connection The connection used for session.
     * @param id The session ID. -1 means none.
     */
    public Session(Connection connection, int id)
    {
        this.connection = connection;
        this.id = id;
    }

    /**
     * Gets the connection for this instance.
     *
     * @return The connection.
     */
    public Connection getConnection()
    {
        return this.connection;
    }

    /**
     * Sets the id for this instance.
     *
     * @param id The id.
     */
    public void setId(int id)
    {
        this.id = id;
    }
}
