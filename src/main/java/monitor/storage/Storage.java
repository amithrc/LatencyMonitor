package main.java.monitor.storage;

import main.java.monitor.container.TimeStamp;
import main.java.monitor.container.TimeStampContainer;

import java.util.LinkedHashMap;
import java.util.logging.Logger;

/**
 * Storage class which holds the packet. This class is thread safe.
 * Sender and receiver threads updates and retrieves a packet
 *
 * @author Amith
 */
public class Storage {

    private Logger logger = null;
    private LinkedHashMap<Long, TimeStampContainer> table = null;

    /**
     * Constructors that's takes the logger and instantiate storage class.
     *
     * @param logger - Takes in logger object.
     */
    public Storage(Logger logger) {

        table = new LinkedHashMap<>();
        this.logger = logger;
    }

    /**
     * Checks if the packet contains the packet already
     *
     * @param id - packet Identifier
     * @return - Returns a boolean value
     */
    synchronized public boolean hasPacket(long id) {
        return table.containsKey(id);
    }


    /**
     * Removes the packet from the Storage.
     *
     * @param packetID - Takes the packetID to remove
     */
    synchronized public void removePacket(long packetID) {
        table.remove(packetID);
    }

    /**
     * Returns the timestamp container.
     *
     * @param packetID - PacketID
     * @return - TimeStampContainer which holds all the traffic
     */

    synchronized public TimeStampContainer getTimeStamp(long packetID) {
        return table.get(packetID);
    }

    /**
     * Adding the packet to the Storage
     *
     * @param id        - PacketID to add.
     * @param timeStamp - Either T1 or T2 timestamp.
     * @param isSender  - if passed true, the sender is updating the packet, if false, receiver is updating the packet
     */

    synchronized public void addPacket(Long id, TimeStamp timeStamp, boolean isSender) {
        if (isSender) {
            TimeStampContainer container = new TimeStampContainer();
            container.setT1(timeStamp);
            table.put(id, container);
        } else {
            TimeStampContainer container = new TimeStampContainer();
            container.setT2(timeStamp);
            table.put(id, container);
        }
    }
}
