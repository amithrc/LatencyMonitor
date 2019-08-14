package main.java.commandparser;

import com.beust.jcommander.JCommander;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * Command Parser which reads the args and returns the config class which holds all the
 * values and its sets up the Logger globally
 * @author Amith
 */
public class CommandParser {

    private JCommander parse = null;
    private Config config = null;
    private String[] args = null;

    /**
     * Constructor that init the config class and sets up the logger
     *
     * @param args - Command  line passed into program
     */
    public CommandParser(String... args) {
        config = new Config();
        this.args = args;
        parse = createParser();
        if (parse != null) {
            config.setParser(parse);
            config.setLogger(createLogger());
        }
    }


    /**
     * Creates the parser object and parse all the command line option
     *
     * @return - Jcommander object which holds all the config
     */
    private JCommander createParser() {
        if (parse == null) {
            parse = JCommander.newBuilder()
                    .addObject(config)
                    .build();
            try {
                parse.parse(args);
            } catch (com.beust.jcommander.ParameterException pe) {
                pe.getMessage();
                pe.usage();
            }

        }
        return parse;
    }

    /**
     * Sets the logger
     *
     * @param isVerbose of verbose is enabled, it will change the log level to Finest to log more details.
     * @param isStdout  outputs the logger into stdout if its set to true
     * @return Returns the Logger object
     */
    private Logger setupLogger(boolean isVerbose, boolean isStdout) {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setUseParentHandlers(false);
        FileHandler fh;
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");

        if (isStdout) {
            logger.setUseParentHandlers(true);
        }

        if (isVerbose) {
            try {
                fh = new FileHandler("latencymonitor.log");
                logger.addHandler(fh);
                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);
            } catch (IOException e) {
                e.printStackTrace();
            }

            logger.setLevel(Level.FINEST);
        } else {
            logger.setLevel(Level.SEVERE);
        }
        return logger;
    }

    /**
     * Return the logger object which is global for entire program
     *
     * @return Logger object and this object will be set in the config class
     */
    private Logger createLogger() {

        if (config.isVerboseEnabled() && config.isStdOutEnabled()) {
            return setupLogger(true, true);
        } else if (config.isVerboseEnabled() && !config.isStdOutEnabled()) {
            return setupLogger(true, false);
        } else if (!config.isVerboseEnabled() & config.isStdOutEnabled()) {
            return setupLogger(false, true);
        }
        return setupLogger(false, false);
    }

    /**
     * Returns the config object that is parsed and config class has getter/setter methods
     * to access its values
     *
     * @return
     */
    public Config getConfig() {
        return config;
    }


}
