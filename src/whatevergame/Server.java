package whatevergame;

import logging.Logger;

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
        if (args.length > 0)
        {
            if ("debug".equals(args[0]))
                Logger.setLogLevel(Logger.LEVEL_DEBUG);
            else if ("start".equals(args[0]))
                Logger.setLogLevel(Logger.LEVEL_START);
            else if ("info".equals(args[0]))
                Logger.setLogLevel(Logger.LEVEL_INFO);
            else if ("comment".equals(args[0]))
                Logger.setLogLevel(Logger.LEVEL_COMMENT);
            else if ("warning".equals(args[0]))
                Logger.setLogLevel(Logger.LEVEL_WARNING);
            else if ("error".equals(args[0]))
                Logger.setLogLevel(Logger.LEVEL_ERROR);
            else if ("off".equals(args[0]))
                Logger.setLogLevel(Logger.LEVEL_OFF);
        }

        new whatevergame.server.Server(3000);
    }
}
