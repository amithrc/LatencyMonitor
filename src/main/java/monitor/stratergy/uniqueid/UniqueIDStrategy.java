package main.java.monitor.stratergy.uniqueid;

import jpcap.packet.Packet;


/**
 * Abstracts class that implements to get a unique ID per packet.
 * The strategy can be changed according to the TrafficGeneratorModel
 */

abstract public class UniqueIDStrategy<T> {

    /**
     * This needs to be implemented by all the sub class
     *
     * @param packet - Takes the packet as argument and returns the Unique ID of the packet
     *               Inspects the packet payload to return the ID
     * @return - return the uniqueID
     */
    public abstract T getPacketID(Packet packet);


}


