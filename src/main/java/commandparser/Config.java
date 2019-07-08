package main.java.commandparser;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=",commandDescription = "Command to Index the Corpus")
public class Config {

    @Parameter(names = {"-is","--interface-sender"},description = "Tap line from the sender", required=true)
    private String interfaceSender;

    @Parameter(names = {"-ir","--interface-receiver"},description = "Tap line from the receiver", required=false)
    private String interfaceReceiver;


    @Parameter(names = {"-ir","--interface-receiver"},description = "Tap line from the receiver")
    private Boolean timeStamp;






}
