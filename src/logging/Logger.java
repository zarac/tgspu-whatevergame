package logging;

import java.text.SimpleDateFormat;

import java.util.Calendar;

/**
 * Creates a logger. One logger should be used per object (for class info).
 * 
 * @author Hannes Landstedt (hannes.landstedt@gmail.com)
 * @version null
 */
public class Logger
{
    /**
     * The log level.
     */
    protected final static int LOG_LEVEL = 5;

    /**
     * Debug log level.
     */
    protected final static int LEVEL_DEBUG = 5;

    /**
     * Informaion log level.
     */
    protected final static int LEVEL_INFO = 4;

    /**
     * Comment log level.
     */
    protected final static int LEVEL_COMMENT = 3;

    /**
     * Warning log level.
     */
    protected final static int LEVEL_WARNING = 2;

    /**
     * Error log level.
     */
    protected final static int LEVEL_ERROR = 1;

    /**
     * Log nothing.
     */
    protected final static int LEVEL_START = 0;

    /**
     * Log nothing.
     */
    protected final static int LEVEL_OFF = -1;

    /**
     * Date format.
     */
    protected static final String DATE_FORMAT = "yyyyMMdd:HHmmss";

    /**
     * The object where the logging is done from.
     */
    protected Object object;

    /**
     * Used to get the timestamp.
     */
    protected Calendar calendar;

    /**
     * Date format.
     */
    protected static SimpleDateFormat dateFormat;

    /**
     * Creates a logger and prints its tag.
     * 
     * @param object The object which will do the logging.
     */
    public Logger(Object object)
    {
        this.object = object;

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat(DATE_FORMAT);
        log(LEVEL_START, object.toString());
    }

    /**
     * Log a message with error level.
     * 
     * @param message The message.
     */
    public void error(String message)
    {
        log(LEVEL_ERROR, message);
    }

    /**
     * Log a message with warning level.
     * 
     * @param message The message.
     */
    public void warning(String message)
    {
        log(LEVEL_WARNING, message);
    }

    /**
     * Log a message with coment level.
     * 
     * @param message The message.
     */
    public void comment(String message)
    {
        log(LEVEL_COMMENT, message);
    }

    /**
     * Log a message with information level.
     * 
     * @param message The message.
     */
    public void info(String message)
    {
        log(LEVEL_INFO, message);
    }

    /**
     * Log a message with debug level.
     * 
     * @param message The message.
     */
    public void debug(String message)
    {
        log(LEVEL_DEBUG, message);
    }

    /**
     * Log a message to a specific level.
     * 
     * @param level The log level.
     * @param message The log message.
     */
    protected void log(int level, String message)
    {
        // Log to stdout
        if (     LEVEL_START   == level && LOG_LEVEL >= level)
            logToConsole("START  ", message);
        else if (LEVEL_ERROR   == level && LOG_LEVEL >= level)
            logToConsole("ERROR  ", message);
        else if (LEVEL_WARNING == level && LOG_LEVEL >= level)
            logToConsole("WARNING", message);
        else if (LEVEL_COMMENT == level && LOG_LEVEL >= level)
            logToConsole("COMMENT", message);
        else if (LEVEL_INFO    == level && LOG_LEVEL >= level)
            logToConsole("INFO   ", message);
        else if (LEVEL_DEBUG   == level && LOG_LEVEL >= level)
            logToConsole("DEBUG  ", message);
    }

    /**
     * Log a message to standard output / console.
     * 
     * @param level The log level.
     * @param message The log message.
     */
    protected void logToConsole(String level, String message)
    {
        System.out.println("\n " + level + " | " + getTimestamp() + " | " + getLogTag() + "\n    " + message);
    }
    
    /**
     * Get a timestamp.
     * 
     * @return The timestamp.
     */
    protected String getTimestamp()
    {
        return dateFormat.format(calendar.getTime());
    }

    /**
     * A representation of this log; the origin.
     * 
     * @return The tag.
     */
    protected String getLogTag()
    {
        return object.getClass().getName();
    }
}
