package main.java.monitor.utils;

import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import main.java.commandparser.Config;
import main.java.monitor.stratergy.uniqueid.UniqueIDStrategy;


public class CaptureTraffic implements PacketReceiver {

    private Config config = null;
    private UniqueIDStrategy uniqueIDStrategy = null;

    public CaptureTraffic(Config config, UniqueIDStrategy uniqueIDStrategy) {
        this.config = config;
        this.uniqueIDStrategy = uniqueIDStrategy;
    }

    @Override
    public void receivePacket(Packet packet) {
        long iid = (long) uniqueIDStrategy.getPacketID(packet);

        if (iid != -1) {
            System.out.println("Packet Identifier IID =  " + iid);
            System.out.println("Time sec: " + packet.sec + " USEC " + packet.usec);
        }
    }
}
