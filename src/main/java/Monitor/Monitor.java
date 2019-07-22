package main.java.Monitor;


import jpcap.JpcapCaptor;
import jpcap.PacketReceiver;
import main.java.Monitor.SetupInterface.SetupInterface;
import main.java.Monitor.helper.Helper;
import main.java.Monitor.packetreceiver.Sender;
import main.java.commandparser.Config;

import java.io.IOException;

public class Monitor {
    private Config config = null;
    private Helper helper = null;

    public Monitor(Config config) {
        this.config = config;
        helper = new Helper(config);
    }

    public void usage() {
        config.usage();
    }

    /**
     * This function handles all the traffic.
     */

    public void handle() throws IOException {

        if (config.isHelp()) {
            this.usage();
        }

        if (config.listInterface()) {
            helper.listInterface();
        }

        if (config.IsMonitorEnabled()) {


            SetupInterface senderInterface = new SetupInterface(config.getInterfaceSender(), config.getTimeStampType());
            JpcapCaptor senderCaptor = senderInterface.getCaptor();


            Sender sender = new Sender();

            Thread t = new Thread(() -> senderCaptor.loopPacket(
                    -1,
                    sender
            ));

            t.setName("JpcapRcvThread");
            t.start();
        }

    }
}
