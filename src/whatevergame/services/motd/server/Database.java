package whatevergame.services.motd.server;

import java.sql.ResultSet;
import java.sql.SQLException;

import whatevergame.server.MySqlDatabase;

public class Database extends MySqlDatabase
{
    // CREATE TABLE motd (id INT NOT NULL AUTO_INCREMENT, message varchar(255), PRIMARY KEY(id));
    protected String motdTable = "motd";

    public String getLatestMotd()
    {
        String query = "SELECT message FROM " + motdTable + " LIMIT 1";
        ResultSet resultSet = doSelect(query);
        if (getNumRows(resultSet) == 0)
        {
            logger.warning("no motd");
            return "No message of the day!";
        }
        else
        {
            try
            {
                // there should only be one player (and he is first)
                resultSet.first();
                logger.debug("motd found: motd='" + resultSet.getString("message") + "'");

                String motd = resultSet.getString("message");

                return motd;
            }
            catch (SQLException e)
            {
                logger.error(e.toString());
                return null;
            }
        }
    }
}
