package main.java.monitor.packetconfig;

import main.java.monitor.storage.Storage;

public class PacketConfig {

    private Storage storage = null;
    private PacketFilter packetFilter = null;


    public PacketConfig(Storage storage, PacketFilter packetID) {
        this.storage = storage;
        this.packetFilter = packetID;
    }

    public Storage getStorage() {
        return storage;
    }

    public PacketFilter getPacketFilter() {
        return packetFilter;
    }

}
