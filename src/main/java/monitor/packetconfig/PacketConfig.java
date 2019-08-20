package main.java.monitor.packetconfig;

import com.opencsv.CSVWriter;
import main.java.commandparser.Config;
import main.java.monitor.packetconfig.filter.PacketFilterBase;
import main.java.monitor.storage.Storage;
import main.java.monitor.utils.WriteCSV;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Packetconfig class holds the reference back to the shared object such as storage, packetFilter and writer.
 *
 * @author Amith
 */
public class PacketConfig {

    private Storage storage = null;
    private PacketFilterBase packetFilter = null;
    private WriteCSV csvWriter = null;
    private Config config = null;


    /**
     * Constructor that assigns back the reference  to the shared variable
     *
     * @param storage
     * @param packetFilter
     */
    public PacketConfig(Storage storage, PacketFilterBase packetFilter, Config config) {
        this.storage = storage;
        this.packetFilter = packetFilter;
        this.config = config;

        String[] header = null;
        if (config.isVerboseEnabled()) {
            header = new String[]{"packetid", "latency", "unit", "STI/sec", "STI/subsec", "STI/convertedunit", "RTI/sec", "RTI/subsec", "RTI/convertedunit", "headertype"};
        } else {
            header = new String[]{"packetid", "latency", "unit"};
        }
        this.csvWriter = new WriteCSV(header, config.getStatsfile());
    }

    /**
     * Shared object to store packets
     *
     * @return
     */
    public Storage getStorage() {
        return storage;
    }

    /**
     * It has a packet filter which filters the packets out
     *
     * @return Returns the reference back to the PacketFilterBase class
     */
    public PacketFilterBase getPacketFilter() {
        return packetFilter;
    }

    /**
     * Returns the CSV writer object
     *
     * @return - csvwriter
     */
    public WriteCSV getCsvWriter() {
        return csvWriter;
    }

    /**
     * Returns the config object which holds reference to all values
     *
     * @return - config object
     */
    public Config getConfig() {
        return config;
    }
}
