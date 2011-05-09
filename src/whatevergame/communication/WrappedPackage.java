package whatevergame.communication;

import whatevergame.Package;

public class WrappedPackage
{
    Connection connection;
    // Named _package instead of package due to reserved keyword.
    Package _package;

    //public WrappedPackage(Content content, int serviceId, int sessionId, Connection connection)
    public WrappedPackage(Package p_package, Connection connection)
    {
        _package = p_package;
        this.connection = connection;
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
     * Gets the _package for this instance.
     *
     * @return The _package.
     */
    public Package getPackage()
    {
        return this._package;
    }
}
