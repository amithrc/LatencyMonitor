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
 *
 * @author Amith
 */

public class Monitor {
    private Config config = null;
    private Helper helper = null;
    private Logger log = null;

    /**
     * Constructors that takes the config object
     *
     * @param config - config object that takes the command line arguments
     */
    public Monitor(Config config) {
        this.config = config;
        helper = new Helper(config);
        log = config.getLogger();
    }

    /**
     * Prints the Usage
     */
    public void usage() {
        config.usage();
    }

    private void shutdownStatus() {
        System.out.println("\n");
        System.out.println("Waiting for the threads to complete..");
        System.out.println("Shutting down..");
    }

    /**
     * Writes the debug info.
     */
    private void printConfigInfo() {
        log.log(Level.FINEST, "*****************************Config Information**********************************");
        log.log(Level.FINEST, "Sender Interface: " + config.getInterfaceSender());
        log.log(Level.FINEST, "Receiver Interface: " + config.getInterfaceSender());
        String res = config.getTimeStampType().toString().equalsIgnoreCase("hardware") ? "hardware" : "software";
        log.log(Level.FINEST, "Time-stamp-type: " + res);
        log.log(Level.FINEST, "Time Interval: " + config.getTimeInterval() + "min");
        log.log(Level.FINEST, "File created:" + config.getStatsfile());
        log.log(Level.FINEST, "Verbose Enabled:" + config.isVerboseEnabled());
        log.log(Level.FINEST, "Stdout Enabled:" + config.isStdOutEnabled());
        log.log(Level.FINEST, "Monitor Enabled:" + config.IsMonitorEnabled());

    }

    /**
     * This method returns how to filter the packet
     * to find the packet ID for each packet
     *
     * @return - reference back to the filter
     */
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

            try {
                Thread.sleep(config.getTimeInterval() * 60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            captureCaptor.breakLoop();
            shutdownStatus();
            executor.shutdown();
            System.exit(1);

        }

        /*
            This setups the network interface and starts monitoring the packet
         */
        if (config.IsMonitorEnabled()) {

            log.log(Level.FINEST, "Started Latency Monitor...");

            SetupInterface senderInterface = new SetupInterface(config.getInterfaceSender(), config.getTimeStampType(), log);
            JpcapCaptor senderCaptor = senderInterface.getCaptor();


            SetupInterface receiverInterface = new SetupInterface(config.getInterfaceReceiver(), config.getTimeStampType(), log);
            JpcapCaptor receiverCaptor = receiverInterface.getCaptor();

            log.log(Level.FINEST, "Sender interface:" + senderInterface.getInterfaceName() + "Receiver Interface:" + receiverInterface.getInterfaceName());

            /*
                if --traffic-generator is enabled, programs creates the packets by adding FF FF FF FF the first 4 bytes in tge data payload by default.
                Pattern can be overridden.
            */

            PacketConfig packetConfig = new PacketConfig(new Storage(log), getFilterType());

            ExecutorService executor = null;
            if (config.isTrafficGen()) {
                executor = Executors.newFixedThreadPool(3);
                executor.submit(new TrafficGenerator(config));
                executor.submit(() -> senderCaptor.loopPacket(-1, new Sender(config, packetConfig)));
                executor.submit(() -> receiverCaptor.loopPacket(-1, new Receiver(config, packetConfig)));
            } else {
                executor = Executors.newFixedThreadPool(2);
                executor.submit(() -> senderCaptor.loopPacket(-1, new Sender(config, packetConfig)));
                executor.submit(() -> receiverCaptor.loopPacket(-1, new Receiver(config, packetConfig)));

            }

            /*
                Run the monitor to the time specified in command line with --time-interval or -d ( in minutes)
            */
            try {
                Thread.sleep(config.getTimeInterval() * 60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            senderCaptor.breakLoop();
            receiverCaptor.breakLoop();
            shutdownStatus();
            executor.shutdown();
            System.exit(1);
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
