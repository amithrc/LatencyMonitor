package main.java.commandparser;

import com.beust.jcommander.JCommander;

import java.util.logging.Logger;


/**
 * Command Parser which reads the args and returns the config class which holds all the
 * values
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
     * Return the logger object which is global for entire program
     *
     * @return Logger object and this object will be set in the config class
     */
    private Logger createLogger() {
        Logger log = Logger.getLogger("latencyMonitor");

        if (config.isVerboseEnabled()) {
            System.out.println("Enabled");
        } else {
            System.out.println("NOt enabled");
        }


        return log;
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
