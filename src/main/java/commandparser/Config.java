package main.java.commandparser;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.util.logging.Logger;

/**
 * Config class which holds all the command line options and its values
 */

@Parameters(separators = "=", commandDescription = "Latency Monitor")
public class Config {

    private JCommander parser = null;
    private final Logger logger = null;

    /*
        Time stamp, Software or Hardware
    */

    Config()
    {

    }

    public enum TimeStampType {
        HARDWARE_TIME_STAMP,
        SOFTWARE_TIME_STAMP
    }

    public enum TransportType {
        TCP,
        UDP
    }

    @Parameter(names = {"-is", "--interface-sender"}, description = "Tap line from the sender", required = true)
    private String interfaceSender;

    @Parameter(names = {"-ir", "--interface-receiver"}, description = "Tap line from the receiver")
    private String interfaceReceiver;


    @Parameter(names = {"-s", "--time-stamp"}, description = "software/hardware")
    private String timeStampType = "software";

    @Parameter(names = {"-t", "--trans-type"}, description = "software/hardware")
    private String transportType = "udp";

    @Parameter(names = {"-V", "--verbose"}, description = "Debugging information")
    private Boolean isVerbose = false;

    @Parameter(names = "--help", help = true)
    private boolean help = false;


    public String getInterfaceSender() {
        return interfaceSender;
    }

    public String getInterfaceReceiver() {
        return interfaceReceiver;
    }

    public TimeStampType getTimeStampType() {
        if ("hardware".equalsIgnoreCase(timeStampType)) {
            return TimeStampType.HARDWARE_TIME_STAMP;
        }
        return TimeStampType.SOFTWARE_TIME_STAMP;
    }

    public TransportType getTransportType() {
        if ("tcp".equalsIgnoreCase(transportType)) {
            return TransportType.TCP;
        }
        return TransportType.UDP;
    }

    public boolean isHelp() {
        return help;
    }

    void setParser(JCommander parser) {
        this.parser = parser;
    }

    public void usage() {
        parser.usage();
    }

}
