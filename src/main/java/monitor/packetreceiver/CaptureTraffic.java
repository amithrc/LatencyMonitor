package main.java.monitor.packetreceiver;

import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import main.java.commandparser.Config;
import main.java.monitor.packetconfig.PacketFilterBase;
import main.java.monitor.packetconfig.PacketInfo;


public class CaptureTraffic implements PacketReceiver {


    private Config config = null;
    private PacketFilterBase filter = null;


    public CaptureTraffic(Config config, PacketFilterBase filter) {
        this.config = config;
        this.filter = filter;
    }

    @Override
    public void receivePacket(Packet packet) {
        PacketInfo info = filter.getPacketInfo(packet);
        if (info != null) {
            System.out.println("Packet : " + info.getPacketID());

        }
    }
}
