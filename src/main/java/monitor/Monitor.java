package main.java.monitor;


import jpcap.JpcapCaptor;
import main.java.monitor.packetconfig.PacketConfig;
import main.java.monitor.packetconfig.filter.PacketFilterBase;
import main.java.monitor.packetconfig.filter.PacketFilterSpirent;
import main.java.monitor.packetconfig.filter.PacketFilterSpirentType2;
import main.java.monitor.packetconfig.filter.PacketFilterTrafficGenerator;
import main.java.monitor.packetreceiver.CaptureTraffic;
import main.java.monitor.packetreceiver.Receiver;
import main.java.monitor.packetreceiver.Sender;
import main.java.monitor.storage.Storage;
import main.java.monitor.utils.Helper;
import main.java.commandparser.Config;

import main.java.monitor.utils.SetupInterface;
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


    private void printConfigInfo() {
        log.log(Level.FINEST, "*****************************Config Information**********************************");
        log.log(Level.FINEST, "Sender Interface: " + config.getInterfaceSender());
        log.log(Level.FINEST, "Receiver Interface: " + config.getInterfaceSender());
        String res = config.getTimeStampType().toString().equalsIgnoreCase("hardware") ? "hardware" : "software";
        log.log(Level.FINEST, "Time-stamp-type: " + res);
    }

    private PacketFilterBase getFilterType() {

        if (config.getFilterType() == 1) {
            return new PacketFilterSpirent(config);
        } else if (config.getFilterType() == 2) {
            return new PacketFilterTrafficGenerator(config);
        } else if (config.getFilterType() == 3) {
            return new PacketFilterSpirentType2(config);
        }
        return null;
    }


    /**
     * This function handles all the traffic.
     */

    public void handle() throws IOException {


        if (config.isVerboseEnabled())
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

            SetupInterface captureinterface = new SetupInterface(config.getInterfaceSender(), config.getTimeStampType(), log);
            JpcapCaptor captureCaptor = captureinterface.getCaptor();

            ExecutorService executor = Executors.newFixedThreadPool(2);
            executor.submit(new TrafficGenerator(config));
            executor.submit(() -> captureCaptor.loopPacket(-1, new CaptureTraffic(config, getFilterType())));
        }


        if (config.IsMonitorEnabled()) {

            log.log(Level.FINEST, "Started Latency Monitor...");

            SetupInterface senderInterface = new SetupInterface(config.getInterfaceSender(), config.getTimeStampType(), log);
            JpcapCaptor senderCaptor = senderInterface.getCaptor();


            SetupInterface receiverInterface = new SetupInterface(config.getInterfaceReceiver(), config.getTimeStampType(), log);
            JpcapCaptor receiverCaptor = receiverInterface.getCaptor();

            log.log(Level.FINEST, "Sender interface:" + senderInterface.getInterfaceName() + "Receiver Interface:" + receiverInterface.getInterfaceName());


            /*
            Allocate Storage , this shared between two threads.
            */
            Storage storage = new Storage(log);

            if (storage == null) {
                log.log(Level.SEVERE, "Cannot allocate memory for storing the packets, exiting..");
                System.exit(-1);
            }

            /*
                if --traffic-generator is enabled, programs creates the packets by adding FF FF FF FF the first 4 bytes in tge data payload by default.
                Pattern can be overridden.
            */

            PacketFilterBase filterBase = getFilterType();
            PacketConfig packetConfig = new PacketConfig(storage, filterBase);

            if (config.isTrafficGen()) {
                ExecutorService executor = Executors.newFixedThreadPool(3);
                executor.submit(new TrafficGenerator(config));
                executor.submit(() -> senderCaptor.loopPacket(-1, new Sender(config, packetConfig)));
                executor.submit(() -> receiverCaptor.loopPacket(-1, new Receiver(config, packetConfig)));
            } else {
                ExecutorService executor = Executors.newFixedThreadPool(2);
                executor.submit(() -> senderCaptor.loopPacket(-1, new Sender(config, packetConfig)));
                executor.submit(() -> receiverCaptor.loopPacket(-1, new Receiver(config, packetConfig)));


            }
        }

        /*
         * Generates the traffic with default config
         */
        if (config.isTrafficGen() && !config.IsMonitorEnabled()) {
            log.log(Level.FINEST, "Generating Traffic....");
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.submit(new TrafficGenerator(config));
            executor.shutdown();
        }

    }


}
