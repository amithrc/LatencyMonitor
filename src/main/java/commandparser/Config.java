package main.java.commandparser;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * Config class which holds all the command line options
 */

@Parameters(separators = "=",commandDescription = "Command line Arguments")
public class Config {

    enum TimeStampType {
        HARDWARE_TIME_STAMP,
        SOFTWARE_TIME_STAMP
    }

    @Parameter(names = {"-is","--interface-sender"},description = "Tap line from the sender", required=true)
    private String interfaceSender;

    @Parameter(names = {"-ir","--interface-receiver"},description = "Tap line from the receiver")
    private String interfaceReceiver;


    @Parameter(names = {"-s","--time-stamp"},description = "software/hardware")
    private String timeStampType = "software";





    public String getInterfaceSender() {
        return interfaceSender;
    }

    public String getInterfaceReceiver() {
        return interfaceReceiver;
    }

    public TimeStampType getTimeStampType()
    {
        switch (timeStampType)
        {
            case "hardware":
                return TimeStampType.HARDWARE_TIME_STAMP;

                default: return TimeStampType.SOFTWARE_TIME_STAMP;
        }
    }






}
