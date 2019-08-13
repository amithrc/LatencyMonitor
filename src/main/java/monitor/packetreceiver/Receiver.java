package main.java.monitor.packetreceiver;

import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import main.java.commandparser.Config;
import main.java.monitor.container.TimeStamp;
import main.java.monitor.container.TimeStampContainer;
import main.java.monitor.packetconfig.PacketConfig;
import main.java.monitor.packetconfig.filter.PacketFilterBase;
import main.java.monitor.packetconfig.PacketInfo;
import main.java.monitor.storage.Storage;

import java.io.BufferedWriter;
import java.io.IOException;


public class Receiver implements PacketReceiver {


    private Config config = null;

    private Storage table = null;
    private PacketFilterBase filter = null;
    private BufferedWriter writer = null;

    public Receiver(Config config, PacketConfig packetConfig) {
        this.config = config;
        this.table = packetConfig.getStorage();
        this.filter = packetConfig.getPacketFilter();
        this.writer=packetConfig.getWriter();

    }


    @Override
    public void receivePacket(Packet packet) {

        PacketInfo packetInfo = filter.getPacketInfo(packet);
        if (packetInfo != null) {
            long id = packetInfo.getPacketID();
            System.out.println("Packet receiver: " + packetInfo.getPacketID());
            if (table.hasPacket(packetInfo.getPacketID())) {

                //synchronized (this.writer)
                //{

                //}
                System.out.println("Receiver side Packet ID: "+id +" RTT:"+ (packetInfo.getTimeStamp().getResultTimeUnit() - table.getTable().get(id).getT1().getResultTimeUnit())+" "+config.getUnitString());
                table.getTable().remove(id);
            } else {
                table.addPacket(packetInfo.getPacketID(), packetInfo.getTimeStamp(), false);
            }
        }
    }
}
