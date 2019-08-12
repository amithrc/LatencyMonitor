package main.java.monitor.packetreceiver;

import main.java.commandparser.Config;
import main.java.monitor.container.TimeStampContainer;
import main.java.monitor.storage.Storage;


/**
 * This class performs all the calculation and statistics
 *
 * @author Amith
 */
public class CalculationManager implements Runnable {

    private Config config = null;
    private Storage table = null;

    public CalculationManager(Config config, Storage table) {
        this.config = config;
        this.table = table;
    }


    synchronized void doStatistics() {

        for (Long packetID : table.getTable().keySet()) {
            TimeStampContainer container = table.getTable().get(packetID);
            if (container.getT1() != null && container.getT2() != null) {
                System.out.println("Packet ID :" + packetID + " RTT:" + (container.getT2().getResultTimeUnit() - container.getT1().getResultTimeUnit()) + " " + config.getUnitString());
                table.getTable().remove(packetID);
            }

        }

    }

    @Override
    public void run() {
        doStatistics();

    }
}
