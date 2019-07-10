package main.java.commandparser;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * Config class which holds all the command line options and its values
 */

@Parameters(separators = "=",commandDescription = "Latency Monitor")
public class Config {

    /*
        Time stamp, Software or Hardware
    */

    public enum TimeStampType {
        HARDWARE_TIME_STAMP,
        SOFTWARE_TIME_STAMP
    }

    public enum TransportType {
        TCP,
        UDP
    }

    @Parameter(names = {"-is","--interface-sender"},description = "Tap line from the sender", required=true)
    private String interfaceSender;

    @Parameter(names = {"-ir","--interface-receiver"},description = "Tap line from the receiver")
    private String interfaceReceiver;


    @Parameter(names = {"-s","--time-stamp"},description = "software/hardware")
    private String timeStampType = "software";

    @Parameter(names = {"-t","--trans-type"},description = "software/hardware")
    private String transportType = "udp";




    public String getInterfaceSender() {
        return interfaceSender;
    }

    public String getInterfaceReceiver() {
        return interfaceReceiver;
    }

    public TimeStampType getTimeStampType()
    {
        if ("hardware".equalsIgnoreCase(timeStampType)) {
            return TimeStampType.HARDWARE_TIME_STAMP;
        }
        return TimeStampType.SOFTWARE_TIME_STAMP;
    }

    public TransportType getTransportType()
    {
        if("tcp".equalsIgnoreCase(transportType))
        {
            return TransportType.TCP;
        }
        return TransportType.UDP;
    }

}
