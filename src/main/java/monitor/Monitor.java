package main.java.monitor;


import jpcap.JpcapCaptor;
import main.java.monitor.SetupInterface.SetupInterface;
import main.java.monitor.helper.Helper;
import main.java.monitor.packetreceiver.Sender;
import main.java.commandparser.Config;
import main.java.trafficgenerator.TrafficGenerator;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

            log.log(Level.FINEST, "Executing Latency Monitor");

            SetupInterface senderInterface = new SetupInterface(config.getInterfaceSender(), config.getTimeStampType(), log);
            JpcapCaptor senderCaptor = senderInterface.getCaptor();

            log.log(Level.FINEST, "Sender Interface: " + senderInterface.getInterfaceName());


            ExecutorService executor = Executors.newFixedThreadPool(2);
            executor.submit(new TrafficGenerator(config));
            executor.submit(() -> senderCaptor.loopPacket(-1, new Sender(config)));

            executor.shutdown();
        }


        if (config.isTrafficGen()) {
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.submit(new TrafficGenerator(config));
            executor.shutdown();
        }

    }


}
