package main.java;

import main.java.Monitor.Monitor;
import main.java.commandparser.CommandParser;
import main.java.commandparser.Config;


/**
 * Main class which takes the arguments and convert that as the config class
 */
public class projectMain {

    public static void main(String[] args) {

        CommandParser parser = new CommandParser(args);

        Config config = parser.getConfig();
        Monitor monitor = new Monitor(config);

        if (args.length < 1) {
            monitor.usage();
        }else {
            monitor.handle();
        }
    }
}