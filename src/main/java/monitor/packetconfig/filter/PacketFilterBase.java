package main.java.monitor.packetconfig.filter;

import jpcap.JpcapPacket;
import main.java.commandparser.Config;
import main.java.monitor.packetconfig.PacketInfo;

import java.util.Arrays;


/**
 * Abstract PacketFilter class which will return the PacketInfo object
 * All subclass that extends this needs to implement  how the packet is filtered out
 * and it is responsible to create timestamps
 */

abstract public class PacketFilterBase {

    /**
     * Returns the packet ID from the data buffer (JpcapPacket data)
     * Filter type 3 indicates Downstream data, this data has VLAN tagging, So it include 4 bytes in the data payload
     * Filter type 4 indicates Upstream capture , this does not have VLAN information.
     *
     * @param data       Data buffer of each packet
     * @param type       Header Type used, packet can have just ipv4 header with Source IP increments or TCP header with Sequence number increments
     * @param filterType Downstream capture or upstream capture
     * @return
     */
    byte[] extractPacketID(byte[] data, Config.HeaderType type, int filterType) {
        if (type == Config.HeaderType.IPV4_header && filterType == 3) {
            return Arrays.copyOfRange(data, 16, 20);
        } else if (type == Config.HeaderType.TCP_HEADER && filterType == 3) {
            return Arrays.copyOfRange(data, 28, 32);
        } else if (type == Config.HeaderType.IPV4_header && filterType == 4) {
            return Arrays.copyOfRange(data, 12, 16);
        } else if (type == Config.HeaderType.TCP_HEADER && filterType == 4) {
            return Arrays.copyOfRange(data, 24, 28);
        }
        return null;
    }


    /**
     * Converts the given time to the time unit passed in.
     *
     * @param isHWTimeSTamp
     * @param sec
     * @param usec
     * @return returns the converted time
     */
    protected long convertTimeUnit(boolean isHWTimeSTamp, Config.TimeUnit timeUnit, long sec, long usec) {
        long convertedValue = 0L;

        if (timeUnit == Config.TimeUnit.MICRO_SEC) {
            long secres = sec * 1000000;
            convertedValue = secres + (isHWTimeSTamp ? (usec / 1000) : usec);
        } else if (timeUnit == Config.TimeUnit.NANO_SEC) {
            long secres = sec * 1000000000;
            convertedValue = secres + (isHWTimeSTamp ? usec : usec * 1000);
        } else if (timeUnit == Config.TimeUnit.MILLI_SEC) {
            long secres = sec * 1000;
            convertedValue = secres + (isHWTimeSTamp ? (usec / 1000000) : (usec / 1000));
        } else {
            convertTimeUnit(isHWTimeSTamp, Config.TimeUnit.NANO_SEC, sec, usec);
        }
        return convertedValue;
    }


    /**
     * Returns the packetInfo if the packet in question is part of the testing
     *
     * @param packet - Raw packet as input
     * @return - PacketInfo object
     */
    abstract public PacketInfo getPacketInfo(JpcapPacket packet);
}
