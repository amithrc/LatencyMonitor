package main.java.monitor.stratergy.storage;


/**
 * Interface that defines the storage Strategy
 */
public interface StorageStrategy {

    /**
     * Stores the packet when it arrives on the sender interface
     *
     * @param iid       - Unique ID of the packet
     * @param timeStamp - Epoch time of the unix
     */

    void putpacket(Long iid, Long timeStamp);

    /**
     * Matches the packet when it arrives on the receiver interface and returns the time
     *
     * @param iid
     * @return return the Epoc time to calculate the difference
     */
    Long getpacket(Long iid);

}
