package main.java.commandparser;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.util.logging.Logger;

/**
 * Config class which holds all the command line options and its values
 * @author Amith
 */

@Parameters(separators = "=", commandDescription = "Latency monitor")
public class Config {

    private JCommander parser = null;
    private Logger logger = null;

    /*
        Time stamp, Software or Hardware
    */

    public enum TimeStampType {
        HARDWARE_TIME_STAMP,
        SOFTWARE_TIME_STAMP
    }

    public enum TimeUnit {
        MILLI_SEC,
        MICRO_SEC,
        NANO_SEC,
    }


    @Parameter(names = {"-is", "--interface-sender"}, description = "Tap line from the sender", required = false)
    private String interfaceSender;

    @Parameter(names = {"-ir", "--interface-receiver"}, description = "Tap line from the receiver", required = false)
    private String interfaceReceiver;


    @Parameter(names = {"-s", "--time-stamp"}, description = "software/hardware")
    private String timeStampType = "software";


    @Parameter(names = {"-l", "--list-interface"}, description = "List the available network interface")
    private Boolean isListInterfaceEnabled = false;

    @Parameter(names = {"-m", "--monitor"}, description = "Pass this argument to monitor the traffic")
    private Boolean isMonitorEnabled = false;

    @Parameter(names = {"-cap", "--capture"}, description = "Capture traffic on the given interface")
    private Boolean isCaptureEnabled = false;


    @Parameter(names = {"-d", "--time-interval"}, description = "Program runs for this specified time and all the threads will exit gracefully")
    private int timeInterval = 60;

    @Parameter(names = {"-n", "--pack-count"}, description = "Number of packets to generate")
    private int numberOfPackets = 500;

    @Parameter(names = {"-S", "--sleep-time"}, description = "Thread sleep time, relevant to generate traffic")
    private int threadSleepTime = 1;

    @Parameter(names = {"-f", "--filter-type"}, description = "Filter type to apply, Spirent 1, Traffic Generator 2")
    private int filterType = 1;

    @Parameter(names = {"-t", "--time-unit"}, description = "Time unit (ms,us,ns)")
    private String timeUnit = "us";


    @Parameter(names = {"--uid-pattern"}, description = "Unique ID pattern")
    private String uidpattern = "ffff";

    @Parameter(names = {"-g", "--generate-traffic"}, description = "Generates the Ethernet traffic")
    private Boolean isTrafficGen = false;


    @Parameter(names = "--help", help = true)
    private boolean help = false;

    @Parameter(names = {"-V", "--verbose"}, description = "Debugging information")
    private Boolean isVerbose = false;

    @Parameter(names = {"-out", "--stdout"}, description = "Logs all the information into stdout")
    private Boolean isStdOut = false;


    /*
       All getter methods
    */
    public String getInterfaceSender() {
        return interfaceSender;
    }

    public TimeUnit getTimeUnit() {
        if ("ms".equalsIgnoreCase(timeUnit)) {
            return TimeUnit.MILLI_SEC;
        } else if ("ns".equalsIgnoreCase(timeUnit)) {
            return TimeUnit.NANO_SEC;
        }
        return TimeUnit.MICRO_SEC;
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

    public String getUnitString() {
        if ("ms".equalsIgnoreCase(timeUnit)) {
            return "ms";
        } else if ("ns".equalsIgnoreCase(timeUnit)) {
            return "ns";
        }
        return "us";
    }

    public String getTimeStampTypeString() {
        if ("hardware".equalsIgnoreCase(timeStampType)) {
            return "Hardware";
        }
        return "Software";
    }

    public int getFilterType() {
        return filterType;
    }


    public int getNumberOfPackets() {
        return numberOfPackets;
    }

    public String getUidpattern() {
        return uidpattern;
    }

    public long getThreadSleepTime() {
        return (threadSleepTime * 1000);
    }

    public int getUidLength() {
        return uidpattern.length();

    }

    public boolean isTrafficGen() {
        return isTrafficGen;
    }


    public Boolean listInterface() {
        return isListInterfaceEnabled;
    }

    public Boolean IsMonitorEnabled() {
        return isMonitorEnabled;
    }

    public void usage() {
        parser.usage();
    }


    public boolean isVerboseEnabled() {
        return isVerbose;
    }

    public boolean isStdOutEnabled() {
        return isStdOut;
    }

    public int getTimeInterval() {
        return timeInterval;
    }


    public Boolean IsCaptureEnabled() {
        return isCaptureEnabled;
    }


    public Logger getLogger() {
        return logger;
    }


    /*
        All setter methods
    */

    void setParser(JCommander parser) {
        this.parser = parser;
    }

    void setLogger(Logger logger) {
        this.logger = logger;
    }


    /*
        All helper methods
    */
    public boolean isHelp() {
        return help;
    }


}
