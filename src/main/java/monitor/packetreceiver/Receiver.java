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
        this.writer = packetConfig.getWriter();

    }

    @Override
    public void receivePacket(Packet packet) {

        PacketInfo packetInfo = filter.getPacketInfo(packet);
        if (packetInfo != null) {

            long packetID = packetInfo.getPacketID();
            TimeStamp T2 = packetInfo.getTimeStamp();


            if (table.hasPacket(packetInfo.getPacketID())) {
                TimeStamp T1 = table.getTable().get(packetID).getT1();

                if (config.isStdOutEnabled()) {
                    System.out.println("Packet received on receiver Interface: " + packetID);
                    System.out.println("PacketID:" + packetID + " RTT:" + (T2.getResultTimeUnit() - T1.getResultTimeUnit()) + " " + config.getUnitString());
                }

                try {
                    writer.write("PacketID:" + packetID + " RTT:" + (T2.getResultTimeUnit() - T1.getResultTimeUnit()) + " " + config.getUnitString() + "\n");
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                table.removePacket(packetID);
            } else {
                table.addPacket(packetID, T2, false);
            }
        }
    }
}
