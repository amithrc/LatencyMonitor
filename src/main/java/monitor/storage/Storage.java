package main.java.monitor.storage;

import main.java.monitor.container.TimeStamp;
import main.java.monitor.container.TimeStampContainer;

import java.util.LinkedHashMap;
import java.util.logging.Logger;

public class Storage {

    private Logger logger = null;
    private LinkedHashMap<Long, TimeStampContainer> table = null;

    public Storage(Logger logger) {

        table = new LinkedHashMap<>();
        this.logger = logger;
    }

    synchronized public boolean addPacket(Long id, TimeStamp T1) {
        TimeStampContainer container = new TimeStampContainer();
        container.setT1(T1);

        TimeStampContainer stamp = table.put(id, container);
        if (stamp != null) {
            return true;
        }
        return false;
    }

    synchronized public TimeStampContainer getPacket(Long id, TimeStamp T2) {
        if (table.containsKey(id)) {
            TimeStampContainer container = table.get(id);
            container.setT2(T2);
            return container;
        }
        return null;
    }
}
