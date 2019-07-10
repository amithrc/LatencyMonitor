package main.java.runnner;


import main.java.commandparser.CommandParser;
import main.java.commandparser.Config;

public class Monitor  implements ProgramRunner {
    private Config config = null;

    public Monitor(CommandParser parser)
    {
        config = parser.getConfig();
    }

    @Override
    public void run() {

        System.out.println(config.getInterfaceSender());
        System.out.println(config.getTimeStampType());

        if (config.getTimeStampType() == Config.TimeStampType.SOFTWARE_TIME_STAMP)
        {
            System.out.println("True software");
        }else
        {
            System.out.println("True hardware");
        }

        System.out.println(config.getTransportType());



    }
}
