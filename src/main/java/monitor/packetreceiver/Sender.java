package main.java.monitor.packetreceiver;

import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import main.java.commandparser.Config;
import main.java.monitor.stratergy.storage.StorageStrategy;
import main.java.monitor.stratergy.uniqueid.UniqueIDStrategy;
import main.java.monitor.utils.PacketConfig;


public class Sender implements PacketReceiver {

    private Config config = null;
    private StorageStrategy storage = null;
    private UniqueIDStrategy uniqueIDStrategy = null;


    public Sender(Config config, PacketConfig packetConfig) {
        this.config = config;
        this.storage = packetConfig.getStorage();
        this.uniqueIDStrategy = packetConfig.getUniqueIDStrategy();
    }

    @Override
    public void receivePacket(Packet packet) {
        long iid = (long) uniqueIDStrategy.getPacketID(packet);

        if (iid != -1) {
            storage.putpacket(iid, packet.sec);
            System.out.println("Packet Identifier IID =  " + iid + " Value =" + storage.getpacket(iid));
            System.out.println("Time sec: " + packet.sec + " USEC " + packet.usec);
        }
    }
}
