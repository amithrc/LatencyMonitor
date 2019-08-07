package main.java.monitor;


import jpcap.JpcapCaptor;
import main.java.monitor.utils.SetupInterface;
import main.java.monitor.utils.Helper;
import main.java.monitor.utils.CaptureTraffic;
import main.java.monitor.utils.PacketConfig;
import main.java.monitor.packetreceiver.Receiver;
import main.java.monitor.packetreceiver.Sender;
import main.java.commandparser.Config;
import main.java.monitor.stratergy.storage.StorageStrategy;
import main.java.monitor.stratergy.storage.StorageType1;
import main.java.monitor.stratergy.uniqueid.Strategy1;
import main.java.monitor.stratergy.uniqueid.UniqueIDStrategy;
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

    private UniqueIDStrategy getUniqueIDStrategy() {

        if (config.getUidStrategy() == 1) {
            return new Strategy1(config);
        }
        return null;
    }

    private void printConfigInfo() {
        log.log(Level.FINEST, "*****************************Config Information**********************************");
        log.log(Level.FINEST, "Sender Interface: " + config.getInterfaceSender());
        log.log(Level.FINEST, "Receiver Interface: " + config.getInterfaceSender());
        String res = config.getTimeStampType().toString().equalsIgnoreCase("hardware") ? "hardware" : "software";
        log.log(Level.FINEST, "Time-stamp-type: " + res);
    }


    /**
     * This function handles all the traffic.
     */

    public void handle() throws IOException {

        printConfigInfo();

        /*
        Prints all the available command line option
         */
        if (config.isHelp()) {
            this.usage();
        }

        /*
            Prints the available interface in the system
         */
        if (config.listInterface()) {
            helper.listInterface();
        }

        /*
           Captures the traffic on the given interface
         */

        if (config.IsCaptureEnabled()) {
            printConfigInfo();

            SetupInterface captureinterface = new SetupInterface(config.getInterfaceSender(), config.getTimeStampType(), log);
            JpcapCaptor captureCaptor = captureinterface.getCaptor();

            UniqueIDStrategy uniqueIDStrategy = getUniqueIDStrategy();
            PacketConfig packetConfig = new PacketConfig(null, uniqueIDStrategy);


            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.submit(() -> captureCaptor.loopPacket(-1, new CaptureTraffic(config, packetConfig)));
        }


        if (config.IsMonitorEnabled()) {

            log.log(Level.FINEST, "Started Latency Monitor...");

            SetupInterface senderInterface = new SetupInterface(config.getInterfaceSender(), config.getTimeStampType(), log);
            JpcapCaptor senderCaptor = senderInterface.getCaptor();


            SetupInterface receiverInterface = new SetupInterface(config.getInterfaceReceiver(), config.getTimeStampType(), log);
            JpcapCaptor receiverCaptor = receiverInterface.getCaptor();

            log.log(Level.FINEST, "Sender interface:" + senderInterface.getInterfaceName() + "Receiver Interface:" + receiverInterface.getInterfaceName());


            StorageStrategy storage = getStorageStrategy();

            if (storage == null) {
                log.log(Level.SEVERE, "Cannot allocate memory for storing the packets, exiting..");
                System.exit(-1);
            }

            /*
                if --traffic-generator is enabled, programs creates the packets by adding FF FF FF FF the first 4 bytes in tge data payload by default.
                Pattern can be overridden.
            */

            PacketConfig packetConfig = new PacketConfig(storage, new Strategy1(config));

            if (config.isTrafficGen()) {
                ExecutorService executor = Executors.newFixedThreadPool(3);
                executor.submit(new TrafficGenerator(config));
                executor.submit(() -> senderCaptor.loopPacket(-1, new Sender(config, packetConfig)));
                executor.submit(() -> receiverCaptor.loopPacket(-1, new Receiver(config, storage)));
            } else {
                ExecutorService executor = Executors.newFixedThreadPool(2);
                executor.submit(() -> senderCaptor.loopPacket(-1, new Sender(config, packetConfig)));
                executor.submit(() -> receiverCaptor.loopPacket(-1, new Receiver(config, storage)));
            }

        }

        /*
         * Generates the traffic with default config
         */
        if (config.isTrafficGen()) {
            log.log(Level.FINEST, "Generating Traffic....");
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.submit(new TrafficGenerator(config));
            executor.shutdown();
        }

    }


}
