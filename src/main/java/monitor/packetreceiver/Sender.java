package main.java.monitor.packetreceiver;

import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import main.java.commandparser.Config;
import main.java.monitor.packetconfig.PacketConfig;
import main.java.monitor.storage.Storage;


public class Sender implements PacketReceiver {

    private Config config = null;
    private Storage storage = null;
    private PacketID uniqueIDStrategy = null;


    public Sender(Config config, PacketConfig packetConfig) {
        this.config = config;
        this.storage = packetConfig.getStorage();
        this.uniqueIDStrategy = packetConfig.getUniqueIDStrategy();
    }

    @Override
    public void receivePacket(Packet packet) {
////        long iid = (long) uniqueIDStrategy.getPacketID(packet);
////
////        if (iid != -1) {
////            storage.putpacket(iid, packet.sec);
////            System.out.println("Packet Identifier IID =  " + iid + " Value =" + storage.getpacket(iid));
////            System.out.println("Time sec: " + packet.sec + " USEC " + packet.usec);
//        }
    }
}
