package main.java.Monitor;


import main.java.commandparser.Config;

public class Monitor {
    private Config config = null;

    public Monitor(Config config)
    {
        this.config =config;
    }

    public void usage()
    {
        config.usage();
    }

    /**
     * This function handles all the traffic.
     */

    public void handle() {

        if(config.isHelp())
        {
            this.usage();
        }



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
