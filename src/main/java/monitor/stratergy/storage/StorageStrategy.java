package main.java.monitor.stratergy.storage;


/**
 * Interface that defines the storage Strategy
 */
public interface StorageStrategy<T,V>{

    /**
     * Stores the packet when it arrives on the sender interface
     *
     * @param iid       - Unique ID of the packet
     * @param timeStamp - Epoch time of the unix
     */

    void putpacket(T iid, V timeStamp);

    /**
     * Matches the packet when it arrives on the receiver interface and returns the time
     *
     * @param iid
     * @return return the Epoc time to calculate the difference
     */
    V getpacket(T iid);

}
