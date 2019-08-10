package main.java.monitor.packetconfig.filter;

import jpcap.packet.Packet;
import main.java.commandparser.Config;
import main.java.monitor.packetconfig.PacketInfo;


/**
 * Abstract PacketFilter class which will return the PacketInfo object
 * All subclass that extends this needs to implement  how the packet is filtered out
 * and it is responsible to create timestamps
 */

abstract public class PacketFilterBase {


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
