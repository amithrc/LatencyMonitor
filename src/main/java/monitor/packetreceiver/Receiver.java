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


public class Receiver implements PacketReceiver {


    private Config config = null;

    private Storage table = null;
    private PacketFilterBase filter = null;

    public Receiver(Config config, PacketConfig packetConfig) {
        this.config = config;
        this.table = packetConfig.getStorage();
        this.filter = packetConfig.getPacketFilter();

    }

    @Override
    public void receivePacket(Packet packet) {

        PacketInfo packetInfo = filter.getPacketInfo(packet);
        if (packetInfo != null) {
            TimeStamp T2 = packetInfo.getTimeStamp();
            long id = packetInfo.getPacketID();
            TimeStampContainer ts = table.getPacket(packetInfo.getPacketID(), T2);
            long t1 = ts.getT1().getResultTimeUnit();
            long t2 = T2.getResultTimeUnit();

            System.out.println("Packet ID: " + id + " RTT: " + (t2 - t1) + "  " + config.getUnitString());
        }
    }
}
