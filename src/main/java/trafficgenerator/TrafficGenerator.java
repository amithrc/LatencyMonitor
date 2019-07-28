package main.java.trafficgenerator;


import jpcap.JpcapSender;
import jpcap.packet.Packet;
import main.java.commandparser.Config;
import main.java.monitor.SetupInterface.SetupInterface;
import main.java.monitor.helper.ByteOperation;
import main.java.monitor.stratergy.UniqueIDStrategy;

import java.io.IOException;
import java.math.BigInteger;
import java.util.logging.Logger;

/**
 * This class generates the traffic artificially on a given interface
 * Keep pushing the traffic, A byte array puts sequence number in it
 */

public class TrafficGenerator implements Runnable {
    private Config config = null;
    private Logger logger = null;
    private int numberOfPackets;

    public TrafficGenerator(Config config) {
        this.config = config;
        logger = config.getLogger();
        this.numberOfPackets = config.getNumberOfPackets();
    }

    private byte[] getPacket(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    /**
     * Generates the traffic based on the numberofpackets sets
     * Generates the integer as bytes and sends the packet on the wire
     */
    private void sendTraffic() {


        SetupInterface senderInterface = new SetupInterface(config.getInterfaceSender(), config.getTimeStampType(), logger);
        JpcapSender sender = senderInterface.getSender();


        for (int i = 0; i < numberOfPackets; i++) {
            Packet packet = new Packet();

            byte[] uid = config.getUidpattern().getBytes() /*ByteOperation.hexToByte(this.uidPattern)*/;

            System.out.println("Bytes to string is "+ new String(uid));
            System.out.println("Length of ID ="+ uid.length);
            BigInteger bigInteger = BigInteger.valueOf(i);
            byte[] iid = bigInteger.toByteArray();
            packet.data = ByteOperation.concatByteArray(uid, iid);

            try {
                sender.sendPacket(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(config.getThreadSleepTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
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


