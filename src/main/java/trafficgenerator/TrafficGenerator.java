package main.java.trafficgenerator;


import jpcap.JpcapSender;
import jpcap.packet.Packet;
import main.java.commandparser.Config;
import main.java.monitor.utils.SetupInterface;
import main.java.monitor.utils.ByteOperation;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class generates the traffic artificially on a given interface
 * Keep pushing the traffic, A byte array puts sequence number in it
 */

public class TrafficGenerator implements Runnable {
    private Config config = null;
    private Logger logger = null;
    private int numberOfPackets;
    private String uidpattern = null;

    /**
     * Constructor that takes the config  object to access the command line value
     *
     * @param config - Object to the config class
     */

    public TrafficGenerator(Config config) {
        this.config = config;
        logger = config.getLogger();
        this.numberOfPackets = config.getNumberOfPackets();
        uidpattern = config.getUidpattern();
    }

    /**
     * Generates the traffic based on the numberofpackets sets
     * Generates the integer as bytes and sends the packet on the wire
     */
    private void sendTraffic() {


        SetupInterface senderInterface = new SetupInterface(config.getInterfaceSender(), config.getTimeStampType(), logger);
        JpcapSender sender = senderInterface.getSender();


        for (long i = 0; i < numberOfPackets; i++) {
            Packet packet = new Packet();

            byte[] mac = ByteOperation.getMacHeader(senderInterface.getSourceMac());
            byte[] uid = ByteOperation.getBytes(uidpattern);
            byte[] iid = ByteOperation.getIID(i);
            packet.data = ByteOperation.concatByteArray(mac, uid, iid);

            /* Debugging message */
            logger.log(Level.FINEST, "------------------------------------------------------------");
            logger.log(Level.FINEST, "PacketID: " + i + " UID pattern used:" + uidpattern);
            logger.log(Level.FINEST, "Header length: " + mac.length + " Data length:" + packet.data.length);
            logger.log(Level.FINEST, "-----------------------------------------------------------");

            try {
                logger.log(Level.FINEST, "Sending data packetID = " + i);
                sender.sendPacket(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                logger.log(Level.FINEST, "Sleeping for ..." + config.getThreadSleepTime() + " sec");
                Thread.sleep(config.getThreadSleepTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Calls SendTraffic() method inside run method
     */
    @Override
    public void run() {
        sendTraffic();
    }
}


