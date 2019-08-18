package main.java.commandparser;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.util.logging.Logger;

/**
 * Config class which holds all the command line options and its values
 *
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
    private int timeInterval = 15;

    @Parameter(names = {"-n", "--pack-count"}, description = "Number of packets to generate")
    private int numberOfPackets = 500;

    @Parameter(names = {"-S", "--sleep-time"}, description = "Thread sleep time, relevant to generate traffic")
    private int threadSleepTime = 1;

    @Parameter(names = {"-f", "--filter-type"}, description = "Filter type to apply, Spirent type 1, Traffic Generator 2, Spirent type 3")
    private int filterType = 1;

    @Parameter(names = {"-t", "--time-unit"}, description = "Time unit (ms,us,ns)")
    private String timeUnit = "us";


    @Parameter(names = {"--uid-pattern"}, description = "Unique ID pattern")
    private String uidpattern = "ffff";

    @Parameter(names = {"--file-name"}, description = "Filename to write the Stats")
    private String statsfile = "LatencyStats";

    @Parameter(names = {"-g", "--generate-traffic"}, description = "Generates the Ethernet traffic")
    private Boolean isTrafficGen = false;


    @Parameter(names = "--help", help = true)
    private boolean help = false;

    @Parameter(names = {"-V", "--verbose"}, description = "Debugging information")
    private Boolean isVerbose = false;

    @Parameter(names = {"-out", "--stdout"}, description = "Logs all the information into stdout")
    private Boolean isStdOut = false;


    /**
     * Returns the sender interface name as a string
     *
     * @return -Sender interface
     */
    public String getInterfaceSender() {
        return interfaceSender;
    }

    /**
     * Timeunit
     *
     * @return returns the time unit based on the command line, default to MICRO_SEC
     */
    public TimeUnit getTimeUnit() {
        if ("ms".equalsIgnoreCase(timeUnit)) {
            return TimeUnit.MILLI_SEC;
        } else if ("ns".equalsIgnoreCase(timeUnit)) {
            return TimeUnit.NANO_SEC;
        }
        return TimeUnit.MICRO_SEC;
    }

    /**
     * Returns the name of the file to save the values
     *
     * @return - A filename
     */
    public String getStatsfile() {
        return statsfile;
    }

    /**
     * Returns the receiver interface name as a string
     *
     * @return - Receiver interface name
     */

    public String getInterfaceReceiver() {
        return interfaceReceiver;
    }

    /**
     * Time stamp type enabled, defaults to software timestamp.
     *
     * @return returns the TimeStampType based on the command line paramater.
     */

    public TimeStampType getTimeStampType() {
        if ("hardware".equalsIgnoreCase(timeStampType)) {
            return TimeStampType.HARDWARE_TIME_STAMP;
        }
        return TimeStampType.SOFTWARE_TIME_STAMP;
    }

    /**
     * Helper function to print to the logs.
     *
     * @return returns the timeunit in use.
     */
    public String getUnitString() {
        if ("ms".equalsIgnoreCase(timeUnit)) {
            return "ms";
        } else if ("ns".equalsIgnoreCase(timeUnit)) {
            return "ns";
        }
        return "us";
    }

    /**
     * Timestamp type enabled
     *
     * @return A string to notify the timestamp type
     */

    public String getTimeStampTypeString() {
        if ("hardware".equalsIgnoreCase(timeStampType)) {
            return "Hardware";
        }
        return "Software";
    }

    /**
     * Which packet filter is enabled, returns it as integer
     *
     * @return - An integer number that are associated with the filter types
     */
    public int getFilterType() {
        return filterType;
    }

    /**
     * Number of packets to capture.
     *
     * @return - Total number of packets to capture, recommended to use the time-interval -d.
     */
    @Deprecated
    public int getNumberOfPackets() {
        return numberOfPackets;
    }

    /**
     * For the filter type 2, it adds the unique signature to the payload
     *
     * @return
     */
    public String getUidpattern() {
        return uidpattern;
    }

    /**
     * Time in seconds for the thread to sleep.
     *
     * @return
     */
    public long getThreadSleepTime() {
        return (threadSleepTime * 1000);
    }

    /**
     * Length of the pattern used
     *
     * @return Length of the pattern.
     */

    public int getUidLength() {
        return uidpattern.length();

    }

    /**
     * TO check if the traffic generator is enabled, programs generates the traffic
     *
     * @return A boolean value which indicates it
     */
    public boolean isTrafficGen() {
        return isTrafficGen;
    }


    /**
     * List the available interfaces on the system
     *
     * @return - true if the option is enabled
     */
    public Boolean listInterface() {
        return isListInterfaceEnabled;
    }

    /**
     * Starts the thread to monitor all the traffic and uses one of the
     * available filter
     *
     * @return - True of the option is enabled
     */
    public Boolean IsMonitorEnabled() {
        return isMonitorEnabled;
    }

    /**
     * Help usage
     */
    public void usage() {
        parser.usage();
    }

    /**
     * Log information, logs more info in the stats file.
     *
     * @return True if it is enabled in command line
     */
    public boolean isVerboseEnabled() {
        return isVerbose;
    }

    /**
     * Prints all the information to the stdout
     *
     * @return True if it is enabled in command line
     */

    public boolean isStdOutEnabled() {
        return isStdOut;
    }

    /**
     * Time the application needs to run
     *
     * @return
     */
    public int getTimeInterval() {
        return timeInterval;
    }

    /**
     * Captures the network traffic using one of the packet Filter type
     *
     * @return True if it is enabled in command line
     */
    public Boolean IsCaptureEnabled() {
        return isCaptureEnabled;
    }

    /**
     * Returns the logger object
     *
     * @return - logger object, global to the whole application
     */
    public Logger getLogger() {
        return logger;
    }


    /*
        All setter methods
    */

    /**
     * Sets parser
     *
     * @param parser - parser object
     */
    void setParser(JCommander parser) {
        this.parser = parser;
    }

    /**
     * Sets the logger
     *
     * @param logger - Reference to logger object
     */

    void setLogger(Logger logger) {
        this.logger = logger;
    }


    /**
     * Prints the available command line option
     *
     * @return
     */
    public boolean isHelp() {
        return help;
    }


}
