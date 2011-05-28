package whatevergame.services.score.server;

import java.sql.ResultSet;
import java.sql.SQLException;

import whatevergame.server.MySqlDatabase;
import whatevergame.server.User;

public class Database extends MySqlDatabase
{
    protected String table = "wgUser";

    public void add(User user, int amount)
    {
        doUpdate("UPDATE " + table + " SET score=score+" + amount + " WHERE username='" + user.getUsername() + "'");
    }

    public String getAll()
    {
        String returnValue = "";

        ResultSet resultSet = doSelect("SELECT username, score FROM " + table);

        if(getNumRows(resultSet) == 0)
            return "NO HIGH SCORE";
        else
        {
            try
            {
                while (resultSet.next())
                {
                    returnValue += resultSet.getString("username") + " : " + resultSet.getString("score") + "\n";
                }
                return returnValue;
            }
            catch (SQLException e)
            {
                logger.error(e.toString());
                return "Error retrieving high score!";
            }
        }
    }
}
