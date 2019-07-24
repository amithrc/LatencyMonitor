package main.java.monitor;


import jpcap.JpcapCaptor;
import main.java.monitor.SetupInterface.SetupInterface;
import main.java.monitor.helper.Helper;
import main.java.monitor.packetreceiver.Sender;
import main.java.commandparser.Config;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This class handles all the features.
 * if --monitor option is enabled, it will start monitoring
 */
public class Monitor {
    private Config config = null;
    private Helper helper = null;
    private Logger log = null;

    public Monitor(Config config) {
        this.config = config;
        helper = new Helper(config);
        log = config.getLogger();
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

            log.log(Level.FINEST, "Welcome to the Latency Monitor");
            log.info("Welcome again");

            SetupInterface senderInterface = new SetupInterface(config.getInterfaceSender(), config.getTimeStampType());
            JpcapCaptor senderCaptor = senderInterface.getCaptor();


            Thread t = new Thread(() -> senderCaptor.loopPacket(
                    -1,
                    new Sender()
            ));

            t.setName("JpcapRcvThread");
            t.start();
        }

    }
}
