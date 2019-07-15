package main.java.Monitor;


import jpcap.JpcapCaptor;
import jpcap.PacketReceiver;
import main.java.Monitor.SetupInterface.SetupInterface;
import main.java.Monitor.helper.Helper;
import main.java.commandparser.Config;

import java.io.IOException;

public class Monitor {
    private Config config = null;
    Helper helper = new Helper(config);

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

    public void handle() throws IOException {

        if(config.isHelp())
        {
            this.usage();
        }

        if(config.listInterface())
        {
            helper.listInterface();
        }

        if(config.IsMonitorEnabled())
        {
            SetupInterface senderInterface = new SetupInterface(config.getInterfaceSender());
            System.out.println(senderInterface);
            System.out.println(senderInterface.getInterfaceName());

            JpcapCaptor captor = JpcapCaptor.openDevice(senderInterface.getNetworkInterface(), 10000,
                    true, 1, false);
            captor.setNonBlockingMode(false);

            final PacketReceiver pr = pack -> {
                //Do something with the packet
                //pack.sec seconds portion
                //pack.usec microsec if software ts, nano if hardware

                System.out.println(pack.sec+"."+pack.usec);
            };

            Thread t = new Thread(() -> captor.loopPacket(
                    -1, //Number of packets to capture, -1 if infinite
                    pr //Function to run for each received packet
            ));

            t.setName("JpcapRcvThread");
            t.start();
        }

//        System.out.println(config.getInterfaceSender());
//        System.out.println(config.getTimeStampType());
//
//        if (config.getTimeStampType() == Config.TimeStampType.SOFTWARE_TIME_STAMP)
//        {
//            System.out.println("True software");
//        }else
//        {
//            System.out.println("True hardware");
//        }
//
//        System.out.println(config.getTransportType());



    }
}
