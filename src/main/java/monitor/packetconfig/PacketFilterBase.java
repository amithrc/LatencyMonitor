package main.java.monitor.packetconfig;

import jpcap.packet.Packet;
import main.java.monitor.container.TimeStamp;

/**
 * Abstract PacketFilter class which will return the PacketInfo object
 * All subclass that extends this needs to implement  how the packet is filtered out
 * and it is responsible to create timestamps
 */

abstract public class PacketFilterBase {


    public long convertTimeUnit(boolean isHWTimeSTamp, TimeStamp timeStamp) {

        return 0L;

    }


    /**
     * Returns the packetInfo if the packet in question is part of the testing
     *
     * @param packet - Raw packet as input
     * @return - PacketInfo object
     */
    abstract public PacketInfo getPacketInfo(Packet packet);
}
