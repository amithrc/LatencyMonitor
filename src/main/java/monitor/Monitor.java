package main.java.monitor;


import jpcap.JpcapCaptor;
import main.java.monitor.SetupInterface.SetupInterface;
import main.java.monitor.helper.Helper;
import main.java.monitor.packetreceiver.Receiver;
import main.java.monitor.packetreceiver.Sender;
import main.java.commandparser.Config;
import main.java.monitor.stratergy.storage.StorageStrategy;
import main.java.monitor.stratergy.storage.StorageType1;
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

    private StorageStrategy getStorageStrategy() {

        if (config.getStoragestrategy() == 1) {
            return new StorageType1(log);
        }
        return null;
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

            log.log(Level.FINEST, "Started Latency Monitor...");

            SetupInterface senderInterface = new SetupInterface(config.getInterfaceSender(), config.getTimeStampType(), log);
            JpcapCaptor senderCaptor = senderInterface.getCaptor();

            SetupInterface receiverInterface = new SetupInterface(config.getInterfaceReceiver(), config.getTimeStampType(), log);
            JpcapCaptor receiverCaptor = receiverInterface.getCaptor();

            log.log(Level.FINEST, "Sender interface:" + senderInterface.getInterfaceName() + "Receiver Interface:");


            StorageStrategy storage = getStorageStrategy();

            if (storage == null) {
                log.log(Level.SEVERE, "Cannot allocate memory for storing the packets, exiting..");
                System.exit(-1);
            }

            ExecutorService executor = Executors.newFixedThreadPool(3);
            executor.submit(new TrafficGenerator(config));
            executor.submit(() -> senderCaptor.loopPacket(-1, new Sender(config, storage)));
            executor.submit(() -> receiverCaptor.loopPacket(-1, new Receiver(config, storage)));

            executor.shutdown();
        }


        if (config.isTrafficGen()) {
            log.log(Level.FINEST, "Generating Traffic....");
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.submit(new TrafficGenerator(config));
            executor.shutdown();
        }

    }


}
