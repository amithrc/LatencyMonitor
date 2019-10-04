package main.java.monitor.packetconfig.filter;

import jpcap.JpcapPacket;
import main.java.commandparser.Config;
import main.java.monitor.container.TimeStamp;
import main.java.monitor.packetconfig.PacketInfo;
import main.java.monitor.utils.ByteOperation;
import main.java.monitor.utils.Constants;

import java.util.Arrays;
import java.util.logging.Logger;


/**
 * Filters out the packet that comes out of the Spirent.
 * Specifically, it captures the Identification fielf of IPv4 header to get the packet ID and convert them back to long
 *
 * @author Amith
 */
public class PacketFilterSpirent extends PacketFilterBase {

    private Config config = null;
    private Logger logger = null;
    private boolean isHw = false;

    /**
     * Constructor that takes config as argument.
     *
     * @param config
     */
    public PacketFilterSpirent(Config config) {
        this.config = config;

        if (config.getTimeStampType() == Config.TimeStampType.HARDWARE_TIME_STAMP) {
            isHw = true;
        }
        this.logger = config.getLogger();

    }

    /**
     * Returns the packet info object which has packetID and a timestamp of the packet
     *
     * @param packet - Raw packet as input
     * @return packetinfo
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
            byte[] packetIDSlice = Arrays.copyOfRange(data, 8, 10);
            long packetID = ByteOperation.getLongID(packetIDSlice);
            //System.out.println("Ether Type: " + ether + " Packet ID:" + packetID);
            long res = convertTimeUnit(isHw, config.getTimeUnit(), packet.sec, packet.usec);
            info = new PacketInfo(packetID, new TimeStamp(packet.sec, packet.usec, res));
        }

        return info;
    }
}
