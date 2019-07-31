package main.java.monitor.packetreceiver;

import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import main.java.commandparser.Config;
import main.java.monitor.stratergy.storage.StorageStrategy;
import main.java.monitor.stratergy.uniqueid.Strategy1;
import main.java.monitor.stratergy.uniqueid.UniqueIDStrategy;


public class Sender implements PacketReceiver {

    private Config config = null;
    private StorageStrategy storage = null;

    public Sender(Config config, StorageStrategy storage) {
        this.config = config;
        this.storage = storage;

    }

    @Override
    public void receivePacket(Packet packet) {
        UniqueIDStrategy strategy = new Strategy1(config);
        long iid = strategy.getPacketID(packet);

        if (iid != -1) {
            storage.putpacket(iid, (float) 0.0);
            System.out.println("Packet Identifier IID =  " + iid + " Value =" + storage.getpacket(iid));
        }
    }
}
