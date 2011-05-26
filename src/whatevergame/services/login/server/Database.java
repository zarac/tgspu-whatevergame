package whatevergame.services.login.server;

import java.sql.ResultSet;
import java.sql.SQLException;

import whatevergame.server.MySqlDatabase;

public class Database extends MySqlDatabase
{
    // CREATE TABLE wgUser (id INT NOT NULL AUTO_INCREMENT, username varchar(255) NOT NULL, password varchar(255) NOT NULL, PRIMARY KEY (id));
    protected String userTable = "wgUser";

    public User getUserByUsername(String p_username)
    {
        String query = "SELECT username,password FROM " + userTable + " WHERE username='" + p_username + "'";
        ResultSet resultSet = doSelect(query);
        if (getNumRows(resultSet) == 0)
        {
            logger.warning("user not found '" + p_username + "'");
            return null;
        }
        else
        {
            try
            {
                // there should only be one player (and he is first)
                resultSet.first();
                logger.debug("user found: username='" + resultSet.getString("username") + "',password='" + resultSet.getString("password") + "'");

                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                return new User(username, password);
            }
            catch (SQLException e)
            {
                logger.error(e.toString());
                return null;
            }
        }
    }

    public User addUser(String username, String password)
    {
        logger.info("Registering user '" + username + "'");
        doInsert("INSERT INTO " + userTable + "(username,password) VALUES('" + username + "', '" + password + "')");
        return getUserByUsername(username);
    }

    public void dumpUserTable()
    {
        logger.debug("dumpUserTable():");
        String query = "SELECT username, password FROM " + userTable;

        ResultSet resultSet = doSelect(query);
        logger.debug("dumpUserTable(): " + resultSet);

        int numRows = getNumRows(resultSet);

        logger.debug("numRows=" + numRows);

        try
        {
            while (resultSet.next())
            {
                logger.debug("username=" + resultSet.getString("username") + " password=" + resultSet.getString("password"));
            }
        }
        catch (SQLException e)
        {
            logger.error(e.toString());
        }
    }
}
