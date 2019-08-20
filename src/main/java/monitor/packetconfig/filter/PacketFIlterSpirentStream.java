package main.java.monitor.packetconfig.filter;

import jpcap.packet.Packet;
import main.java.commandparser.Config;
import main.java.monitor.container.TimeStamp;
import main.java.monitor.packetconfig.PacketInfo;
import main.java.monitor.utils.ByteOperation;
import main.java.monitor.utils.Constants;

import java.util.logging.Logger;

public class PacketFIlterSpirentStream extends PacketFilterBase {

    private Config config = null;
    private Logger logger = null;
    private boolean isHw = false;

    public PacketFIlterSpirentStream(Config config) {
        this.config = config;

        if (config.getTimeStampType() == Config.TimeStampType.HARDWARE_TIME_STAMP) {
            isHw = true;
        }
        this.logger = config.getLogger();

    }

    /**
     * Gets the packet
     *
     * @param packet - Raw packet as input
     * @return Packet info which holds the packet ID and a timestamp
     */
    @Override
    public PacketInfo getPacketInfo(Packet packet) {

        String ether = ByteOperation.getEtherType(packet.header);

        /*
        if the ether type is of type VLAN, this is the packet of interest
        */

        PacketInfo info = null;

        if (ether.equals(Constants.VLAN)) {
            byte[] packetIDSlice = extractPacketID(packet.data, config.getHeaderType(), config.getFilterType());
            if (packetIDSlice == null) {
                System.out.println("Cannot extract the packet");
                System.exit(-1);
            }

            long packetID = ByteOperation.getLongID(packetIDSlice);
            long res = convertTimeUnit(isHw, config.getTimeUnit(), packet.sec, packet.usec);
            info = new PacketInfo(packetID, new TimeStamp(packet.sec, packet.usec, res));
        }
        return info;
    }
}
