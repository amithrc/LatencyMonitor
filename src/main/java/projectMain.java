package main.java;

import main.java.monitor.Monitor;
import main.java.commandparser.CommandParser;
import main.java.commandparser.Config;

import java.io.IOException;
import java.util.logging.Level;


/**
 * Main class which takes the arguments and convert that as the config class
 */
public class projectMain {

    public static void main(String[] args) throws IOException, InterruptedException {

        CommandParser parser = new CommandParser(args);
        Config config = parser.getConfig();

        config.getLogger().log(Level.FINEST,"Starting Latency Monitoring application, Reading the System config");
        Monitor monitor = new Monitor(config);

        if (args.length < 1) {
            monitor.usage();
        } else {
            monitor.handle();
        }
    }
}