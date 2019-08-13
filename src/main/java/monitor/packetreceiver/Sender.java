package main.java.monitor.packetreceiver;

import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import main.java.commandparser.Config;
import main.java.monitor.container.TimeStamp;
import main.java.monitor.packetconfig.PacketConfig;
import main.java.monitor.packetconfig.filter.PacketFilterBase;
import main.java.monitor.packetconfig.PacketInfo;
import main.java.monitor.storage.Storage;

import java.io.BufferedWriter;
import java.io.IOException;


public class Sender implements PacketReceiver {

    private Config config = null;

    private Storage table = null;
    private PacketFilterBase filter = null;
    private BufferedWriter writer=null;


    public Sender(Config config, PacketConfig packetConfig) {
        this.config = config;
        this.table = packetConfig.getStorage();
        this.filter = packetConfig.getPacketFilter();
        this.writer=packetConfig.getWriter();
    }

    /**
     * This methods loops through all the packets that receives on the interface
     * and filters the interested packet. Calling addPacket will add the timestamp T1 for the packet
     *
     * @param packet
     */
    @Override
    public void receivePacket(Packet packet) {

        PacketInfo packetInfo = filter.getPacketInfo(packet);

        if (packetInfo != null) {

            long packetID = packetInfo.getPacketID();
            TimeStamp timeStamp = packetInfo.getTimeStamp();
            System.out.println("Packet Sender: " + packetID);

            if (table.hasPacket(packetID)) {
                System.out.println("Sender Side Packet ID: "+ packetID + " RTT:"+ (table.getTable().get(packetID).getT2().getResultTimeUnit()- timeStamp.getResultTimeUnit()));
                try {
                    writer.write("Sender Side Packet ID: "+ packetID + " RTT:"+ (table.getTable().get(packetID).getT2().getResultTimeUnit()- timeStamp.getResultTimeUnit())+"\n");
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                table.getTable().remove(packetID);
            } else {
                table.addPacket(packetInfo.getPacketID(), packetInfo.getTimeStamp(), true);
            }
        }
    }
}
