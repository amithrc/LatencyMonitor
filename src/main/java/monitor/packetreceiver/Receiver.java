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

    synchronized public boolean hasPacket(long id) {
        return table.getTable().containsKey(id);
    }

    @Override
    public void receivePacket(Packet packet) {

        PacketInfo packetInfo = filter.getPacketInfo(packet);
        if (packetInfo != null) {
            if (hasPacket(packetInfo.getPacketID())) {
                table.addPacket(packetInfo.getPacketID(), packetInfo.getTimeStamp(), false);
            } else {
                table.getTable().get(packetInfo.getPacketID()).setT2(packetInfo.getTimeStamp());
            }
        }
    }
}
