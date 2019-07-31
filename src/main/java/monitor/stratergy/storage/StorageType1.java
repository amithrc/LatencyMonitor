package main.java.monitor.stratergy.storage;

import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StorageType1 extends StorageStrategy {

    private Logger logger = null;
    private LinkedHashMap<Long, Float> table = null;

    public StorageType1(Logger logger) {
        table = new LinkedHashMap<Long, Float>();
        this.logger = logger;
    }

    @Override
    public synchronized void putpacket(Long iid, Float timeStamp) {
        table.put(iid, timeStamp);
        logger.log(Level.FINEST, "Adding IID: " + iid + " Time Stamp: " + timeStamp);
        logger.log(Level.FINEST, "Table size: " + table.size());
    }

    @Override
    public synchronized float getpacket(Long iid) {
        if (table.containsKey(iid)) {
            return table.get(iid);
        }
        return 0;
    }
}

