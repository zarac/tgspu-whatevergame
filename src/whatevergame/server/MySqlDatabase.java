package whatevergame.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logging.Logger;

public class MySqlDatabase
{
    protected Logger logger;

    protected static String host;
    protected static int port;
    protected static String username;
    protected static String password;
    protected static String databaseName;
    protected static String url;
 
    protected static Connection connection;
    protected static Statement statement;
    protected static ResultSet result;

    public MySqlDatabase()
    {
        logger = new Logger(this);
        logger.debug("Say woouut!?");
        if (connection == null)
        {
            logger.warning("Not connected to database!");
        }
    }

    /**
     * Constructs a new instance.
     */
    public MySqlDatabase(String p_host, int p_port, String p_username, String p_password, String p_databaseName)
    {
        init(p_host, p_port, p_username, p_password, p_databaseName);
    }

    public void init(String p_host, int p_port, String p_username, String p_password, String p_databaseName)
    {
        host = p_host;
        port = p_port;
        username = p_username;
        password = p_password;
        databaseName = p_databaseName;

        logger = new Logger(this);

        url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;
        logger.debug("url='" + url + "'");

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            logger.error("MySQL driver not found. " + e);
        }
    }

    public void connect()
    {
        logger.debug("connect(): trying to connect to database");
        if (connection == null)
        {
            try
            {
                connection = DriverManager.getConnection(url, username, password);
                statement = connection.createStatement();
                logger.info("connect(): connected to database");
            }
            catch (SQLException e)
            {
                logger.error("connect(): SQL ERROR : " + e.getMessage() + " : " + e.toString());
            }
        }
        else
        {
            logger.debug("connect(): already connected");
        }
    }

    /**
     * metoden connect kopplar upp mot sql databasen.
     *
     * @param url               vilken url databasen har
     * @param usr               användarnamnet
     * @param pw                lösenordet
     * @throws SQLException     Exception metoden kasstar
     */
    public void connect(String url, String usr, String pw) throws SQLException
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, usr, pw);
            statement = connection.createStatement();
        }
        catch (ClassNotFoundException ex)
        {
            System.out.println("Can not connect: No driver found: "+ex);
        }
    }

    /**
     * disconnect metoden kopplar ner från sql databasen
     *
     * @throws SQLException     Exception metoden kasstar
     */
    public void disconnect() throws SQLException
    {
        statement.close();
        connection.close();
    }

    public int runUpdate(String p_query)
    {
        try
        {
            return statement.executeUpdate(p_query);
        }
        catch (SQLException e)
        {
            System.out.println("SQL Exception: " + e);
        }

        // -1 means error, would be better to return whatever executeQuery() returns.
        return -1;
    }

    /**
     * Get a result from a select statement.
     * 
     * @param query A select statement.
     * @return Would be nicer if it returned an array.. ?
     */
    public ResultSet doSelect(String query)
    {
        try
        {
            logger.debug("connection=" + connection);
            logger.debug("statement=" + statement);
            result = statement.executeQuery(query);
        }
        catch (SQLException ex)
        {
            System.out.println("SQL Exception: "+ex);
        }

        return result;
        //return null;
    }

    /**
     * Run an update query.
     * 
     * @param query The update query.
     * @return Number of rows affected.
     */
    public int doUpdate(String query)
    {
        logger.debug("doUpdate(): " + query);

        try
        {
            return statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            logger.error("doUpdate(): " + e.toString());
        }

        return -1;
    }

    /**
     * Run an insert query.
     * 
     * @param query The insert query.
     */
    public void doInsert(String query)
    {
        logger.debug("doInsert(): " + query);

        try
        {
            statement.execute(query);
        }
        catch (SQLException e)
        {
            logger.error("doInsert(): " + e.toString());
        }
    }

    /**
     * Get row count in a ResultSet. Has the side effect of running first() when done.
     * 
     * @param resultSet Set to count rows for.
     * @return The row count.
     */
    public int getNumRows(ResultSet resultSet)
    {
        try
        {
            //int currentRow = resultSet.getRow();
            resultSet.last();
            int numRows = resultSet.getRow();
            resultSet.first();
            resultSet.previous();
            return numRows;
        }
        catch (SQLException e)
        {
            logger.error("doUpdate(): " + e.toString());
        }

        return 0;
    }

    /**
     * executeSQLquery metoden skickar en String till statement
     * som skickar en query till databasen.
     *
     * @param query    String variabel med queryn som ska skickas
     */
    public ResultSet executeSQLquery(String query)
    {
        try
        {
            result = statement.executeQuery(query);
        }
        catch (SQLException e)
        {
            System.out.println("SQL Exception: " + e);
        }

        return result;
    }

    /**
     * getResultSet returnerar ett resultSet med innehållet svaret
     * på sql queryn.
     *
     * @return      ResultSet
     */
    public ResultSet getResultSet()
    {
        logger.debug("getResultSet():");
        return result;
    }
}
