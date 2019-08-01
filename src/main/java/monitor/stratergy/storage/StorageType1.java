package main.java.monitor.stratergy.storage;

import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Storage Strategy type1
 */
public class StorageType1 implements StorageStrategy<Long, Long> {

    private Logger logger = null;
    private LinkedHashMap<Long, Long> table = null;

    public StorageType1(Logger logger) {
        table = new LinkedHashMap<Long, Long>();
        this.logger = logger;
    }


    @Override
    synchronized public void putpacket(Long iid, Long timeStamp) {
        table.put(iid, timeStamp);
        logger.log(Level.FINEST, "Adding IID: " + iid + " Time Stamp: " + timeStamp);
        logger.log(Level.FINEST, "Table size: " + table.size());

    }

    @Override
    synchronized public Long getpacket(Long iid) {
        if (table.containsKey(iid)) {
            return table.get(iid);
        }
        return null;
    }
}
