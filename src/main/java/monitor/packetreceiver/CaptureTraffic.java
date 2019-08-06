package main.java.monitor.packetreceiver;

import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import main.java.commandparser.Config;
import main.java.monitor.stratergy.uniqueid.UniqueIDStrategy;


public class CaptureTraffic implements PacketReceiver {

    private Config config = null;
    private UniqueIDStrategy uniqueIDStrategy = null;

    public CaptureTraffic(Config config, PacketConfig packetConfig) {
        this.config = config;
        this.uniqueIDStrategy = packetConfig.getUniqueIDStrategy();
    }

    @Override
    public void receivePacket(Packet packet) {
        System.out.println("Is it executing ");
        long iid = (long) uniqueIDStrategy.getPacketID(packet);

        if (iid != -1) {
            System.out.println("Packet Identifier IID =  " + iid);
            System.out.println("Time sec: " + packet.sec + " USEC " + packet.usec);
        }
    }
}
