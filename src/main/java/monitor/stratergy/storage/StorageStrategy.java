package main.java.monitor.stratergy.storage;

/**
 * Abstract class that defines two methods to put the packet timestamp into data structure
 * that sub class implements
 * All the subclass need to make sure it is thread safe
 */

abstract public class StorageStrategy {

    /**
     * Store the packet when it arrives on the interface
     */
    public abstract void putpacket(Long iid, Float timeStamp);

    /**
     * Match the packet from the data structure and return it.
     *
     * @return - latency information
     */
    public abstract float getpacket(Long iid);
}
