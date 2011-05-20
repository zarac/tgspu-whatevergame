package whatevergame;

/**
 * Start a Whatever Game server.
 *
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
public class Server
{
    /**
     * Instantiates a server.
     *
     * TODO : Allow specifying of listening port.
     * 
     * @param args N/A
     */
    public static void main(String[] args)
    {
        new whatevergame.server.Server(3000);
    }
}
