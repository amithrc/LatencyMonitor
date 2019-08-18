package main.java.monitor.packetconfig;

import com.opencsv.CSVWriter;
import main.java.monitor.packetconfig.filter.PacketFilterBase;
import main.java.monitor.storage.Storage;


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
    private BufferedWriter writer = null;
    private CSVWriter csvWriter = null;


    /**
     * Constructor that assigns back the reference  to the shared variable
     *
     * @param storage
     * @param packetFilter
     */
    public PacketConfig(Storage storage, PacketFilterBase packetFilter) {
        this.storage = storage;
        this.packetFilter = packetFilter;

        try {
            this.writer = new BufferedWriter(new FileWriter(new File("latencyFile.csv")));
        } catch (IOException io) {
            io.getStackTrace();
        }

    }

    /**
     * @return Returns the writer object to write to the file
     */

    public BufferedWriter getWriter() {
        return writer;
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

    public CSVWriter getCsvWriter() {
        return csvWriter;
    }
}
