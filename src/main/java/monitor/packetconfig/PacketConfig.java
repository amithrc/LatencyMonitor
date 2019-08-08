package main.java.monitor.packetconfig;

import main.java.monitor.storage.Storage;

public class PacketConfig {

    private Storage storage = null;
    private PacketFilterBase packetFilter = null;


    public PacketConfig(Storage storage, PacketFilterBase packetFilter) {
        this.storage = storage;
        this.packetFilter = packetFilter;
    }

    public Storage getStorage() {
        return storage;
    }

    public PacketFilterBase getPacketFilter() {
        return packetFilter;
    }

}
