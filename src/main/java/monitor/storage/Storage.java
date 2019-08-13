package main.java.monitor.storage;

import main.java.monitor.container.TimeStamp;
import main.java.monitor.container.TimeStampContainer;

import java.util.LinkedHashMap;
import java.util.logging.Logger;

public class Storage {

    private Logger logger = null;
    private LinkedHashMap<Long, TimeStampContainer> table = null;

    public LinkedHashMap<Long, TimeStampContainer> getTable() {
        return table;
    }

    public Storage(Logger logger) {

        table = new LinkedHashMap<>();
        this.logger = logger;
    }

    synchronized public boolean hasPacket(long id) {
        return table.containsKey(id);
    }

    synchronized public void removePacket(long packetID) {
        table.remove(packetID);
    }


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
