package main.java.monitor.packetconfig.filter;

import jpcap.packet.Packet;
import main.java.commandparser.Config;
import main.java.monitor.packetconfig.PacketInfo;

import java.util.Arrays;


/**
 * Abstract PacketFilter class which will return the PacketInfo object
 * All subclass that extends this needs to implement  how the packet is filtered out
 * and it is responsible to create timestamps
 */

abstract public class PacketFilterBase {

    public byte[] extractPacketID(byte[] data, Config.HeaderType type) {
        if (type == Config.HeaderType.IPV4_header) {
            return Arrays.copyOfRange(data, 16, 20);
        } else if (type == Config.HeaderType.TCP_HEADER) {
            return Arrays.copyOfRange(data, 28, 32);
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
    abstract public PacketInfo getPacketInfo(Packet packet);
}
