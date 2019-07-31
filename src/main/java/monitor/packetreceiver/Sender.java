package main.java.monitor.packetreceiver;

import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import main.java.commandparser.Config;
import main.java.monitor.stratergy.uniqueid.Strategy1;
import main.java.monitor.stratergy.uniqueid.UniqueIDStrategy;


public class Sender implements PacketReceiver {

    private Config config = null;

    public Sender(Config config) {
        this.config = config;

    }

    @Override
    public void receivePacket(Packet packet) {
        UniqueIDStrategy strategy = new Strategy1(config);
        long iid = strategy.getPacketID(packet);

        if(iid!=-1)
            System.out.println("Packet Identifier = " + iid);
    }
}
