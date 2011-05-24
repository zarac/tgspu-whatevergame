package whatevergame.services.login.server;

import java.sql.ResultSet;
import java.sql.SQLException;

import whatevergame.server.MySqlDatabase;

public class Database extends MySqlDatabase
{
    protected String userTable = "wgUser";

    public User getUserByUsername(String p_username)
    {
        String query = "SELECT username,password FROM " + userTable + " WHERE username='" + p_username + "'";
        ResultSet resultSet = doSelect(query);

        int numRows = getNumRows(resultSet);

        // make new player
        if (numRows == 0)
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

    public void dumpUserTable()
    {
        logger.debug("dumpUserTable():");
        String query = "SELECT * FROM " + userTable;

        ResultSet resultSet = doSelect(query);
        logger.info("dumpUserTable(): " + resultSet);

        int numRows = getNumRows(resultSet);

        logger.debug("numRows=" + numRows);

        try
        {
            while (resultSet.next())
            {
                logger.warning("resultSet.getString('username'): " + resultSet.getString("username"));
            }
        }
        catch (SQLException e)
        {
            logger.error(e.toString());
        }
    }
}
