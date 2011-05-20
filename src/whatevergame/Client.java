package whatevergame;

/**
 * Starts a Whatever Game client.
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
public class Client
{
    /**
     * TODO : Allow specifying of server IP and port as well as client port used
     * for communication.
     * 
     * @param argv N/A
     */
    public static void main(String[] argv)
    {
        new whatevergame.client.Client("localhost", 3000);
    }
}
