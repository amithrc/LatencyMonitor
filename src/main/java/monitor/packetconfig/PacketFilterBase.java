package main.java.monitor.packetconfig;

import jpcap.packet.Packet;

/**
 * Abstract PacketFilter class which will return the PacketInfo object
 * All subclass that extends this needs to implement  how the packet is filtered out
 * and it is responsible to create timestamps
 */

abstract public class PacketFilterBase {

    /**
     * Returns the packetInfo if the packet in question is part of the testing
     *
     * @param packet - Raw packet as input
     * @return - PacketInfo object
     */
    abstract public PacketInfo getPacketInfo(Packet packet);
}
