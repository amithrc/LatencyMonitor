package main.java.monitor.packetreceiver;

import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import main.java.commandparser.Config;
import main.java.monitor.packetconfig.PacketConfig;
import main.java.monitor.packetconfig.PacketFilterBase;
import main.java.monitor.packetconfig.PacketInfo;
import main.java.monitor.storage.Storage;


public class Sender implements PacketReceiver {

    private Config config = null;

    private Storage table = null;
    private PacketFilterBase filter = null;


    public Sender(Config config, PacketConfig packetConfig) {
        this.config = config;
        this.table = packetConfig.getStorage();
        this.filter = packetConfig.getPacketFilter();
    }

    /**
     * This methods loops through all the packets that receives on the interface
     * and filters the interested packet. Calling addPacket will add the timestamp T1 for the packet
     *
     * @param packet
     */
    @Override
    public void receivePacket(Packet packet) {
        PacketInfo info = filter.getPacketInfo(packet);
//
//        if (!table.addPacket(info.getPacketID(), info.getTimeStamp())) {
//            System.out.println("Error adding the packet: " + info.getPacketID());
//        }

        System.out.println("Packet : "+ info.getPacketID());
    }
}
