package main.java.monitor.packetconfig.filter;

import jpcap.JpcapPacket;
import main.java.commandparser.Config;
import main.java.monitor.container.TimeStamp;
import main.java.monitor.packetconfig.PacketInfo;
import main.java.monitor.utils.ByteOperation;
import main.java.monitor.utils.Constants;

import java.util.Arrays;
import java.util.logging.Logger;

public class PacketFilterSpirentDownStream extends PacketFilterBase {

    private Config config = null;
    private Logger logger = null;
    private boolean isHw = false;

    public PacketFilterSpirentDownStream(Config config) {
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
     * @return JpcapPacket info which holds the packet ID and a timestamp
     */
    @Override
    public PacketInfo getPacketInfo(JpcapPacket packet) {

        String ether = ByteOperation.getEtherType(packet.data);

        byte [] data = Arrays.copyOfRange(packet.data, 14, packet.data.length);

        /*
        if the ether type is of type VLAN, this is the packet of interest
        */

        PacketInfo info = null;

        if (ether.equals(Constants.VLAN)) {
            byte[] packetIDSlice = extractPacketID(data, config.getHeaderType(), config.getFilterType());
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
