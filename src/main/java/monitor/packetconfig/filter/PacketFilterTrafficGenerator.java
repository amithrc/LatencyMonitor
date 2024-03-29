package main.java.monitor.packetconfig.filter;

import jpcap.JpcapPacket;
import main.java.commandparser.Config;
import main.java.monitor.container.TimeStamp;
import main.java.monitor.packetconfig.PacketInfo;
import main.java.monitor.utils.ByteOperation;
import main.java.monitor.utils.Constants;

import java.util.Arrays;

/**
 * Filters the packet generated by the TrafficGenerator class
 * The signature of ffff is appended to the first 4 bytes in the data payload
 *
 * @author Amith
 */
public class PacketFilterTrafficGenerator extends PacketFilterBase {

    private Config config = null;
    private boolean isHw = false;

    /**
     * Constructor that takes in config class as input.
     *
     * @param config
     */
    public PacketFilterTrafficGenerator(Config config) {
        this.config = config;

        if (config.getTimeStampType() == Config.TimeStampType.HARDWARE_TIME_STAMP) {
            isHw = true;
        }
    }

    /**
     * Returns the packetinfo object which has packetID and TimeStamp of the packet.
     *
     * @param packet - Raw packet as input
     * @return
     */

    @Override
    public PacketInfo getPacketInfo(JpcapPacket packet) {
        byte [] data = Arrays.copyOfRange(packet.data, 14, packet.data.length);
        byte[] uuidSlice = Arrays.copyOfRange(data, 0, config.getUidLength());
        byte[] iidSlice = Arrays.copyOfRange(data, config.getUidLength(), config.getUidLength() + 8);
        String uid = new String(uuidSlice);
        String etherType = ByteOperation.getEtherType(packet.data);

        PacketInfo info = null;

        if (uid.equalsIgnoreCase(config.getUidpattern()) && etherType.equalsIgnoreCase(Constants.UNKNOWN)) {
            long iid = ByteOperation.getLongID(iidSlice);
            long res = convertTimeUnit(isHw, config.getTimeUnit(), packet.sec, packet.usec);
            info = new PacketInfo(iid, new TimeStamp(packet.sec, packet.usec, res));
        }
        return info;
    }
}
