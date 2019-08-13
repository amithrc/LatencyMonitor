package main.java.monitor.packetconfig;

import main.java.monitor.packetconfig.filter.PacketFilterBase;
import main.java.monitor.storage.Storage;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PacketConfig {

    private Storage storage = null;
    private PacketFilterBase packetFilter = null;
    private BufferedWriter writer=null;


    public PacketConfig(Storage storage, PacketFilterBase packetFilter) {
        this.storage = storage;
        this.packetFilter = packetFilter;

        try
        {
            this.writer = new BufferedWriter(new FileWriter(new File("latencyFile.csv")));
        }catch(IOException io)
        {
            io.getStackTrace();
        }

    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public Storage getStorage() {
        return storage;
    }

    public PacketFilterBase getPacketFilter() {
        return packetFilter;
    }

}
