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
     * Debug log level.
     */
    public final static int LEVEL_DEBUG = 6;

    /**
     * Log nothing.
     */
    public final static int LEVEL_START = 5;

    /**
     * Informaion log level.
     */
    public final static int LEVEL_INFO = 4;

    /**
     * Comment log level.
     */
    public final static int LEVEL_COMMENT = 3;

    /**
     * Warning log level.
     */
    public final static int LEVEL_WARNING = 2;

    /**
     * Error log level.
     */
    public final static int LEVEL_ERROR = 1;

    /**
     * Log nothing.
     */
    public final static int LEVEL_OFF = 0;

    /**
     * The log level.
     */
    protected static int logLevel = LEVEL_WARNING;

    /**
     * Date format.
     */
    protected static final String DATE_FORMAT = "yyyyMMdd:HHmmss";

    /**
     * The object where the logging is done from.
     */
    protected Object object;

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

        dateFormat = new SimpleDateFormat(DATE_FORMAT);
        log(LEVEL_START, Integer.toHexString(object.hashCode()));
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
        if (     LEVEL_ERROR   == level && logLevel >= level)
            logToConsole("ERROR  ", message);
        else if (LEVEL_WARNING == level && logLevel >= level)
            logToConsole("WARNING", message);
        else if (LEVEL_COMMENT == level && logLevel >= level)
            logToConsole("COMMENT", message);
        else if (LEVEL_INFO    == level && logLevel >= level)
            logToConsole("INFO   ", message);
        else if (LEVEL_START   == level && logLevel >= level)
            logToConsole("START  ", message);
        else if (LEVEL_DEBUG   == level && logLevel >= level)
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
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    /**
     * A representation of this log; the origin.
     * 
     * @return The tag.
     */
    protected String getLogTag()
    {
        return object.getClass().getName() + " @ " + Integer.toHexString(object.hashCode());
    }

    /**
     * Gets the logLevel for this instance.
     *
     * @return The logLevel.
     */
    static public int getLogLevel()
    {
        return logLevel;
    }

    /**
     * Sets the logLevel for this instance.
     *
     * @param logLevel The logLevel.
     */
    static public void setLogLevel(int p_logLevel)
    {
        logLevel = p_logLevel;
    }
}
