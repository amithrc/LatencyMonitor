package main.java.monitor.utils;

import main.java.monitor.stratergy.storage.StorageStrategy;
import main.java.monitor.stratergy.uniqueid.UniqueIDStrategy;

public class PacketConfig {

    private StorageStrategy storage = null;
    private UniqueIDStrategy uniqueIDStrategy = null;


    public PacketConfig(StorageStrategy storage, UniqueIDStrategy uniqueIDStrategy) {
        this.storage = storage;
        this.uniqueIDStrategy = uniqueIDStrategy;
    }

    public StorageStrategy getStorage() {
        return storage;
    }

    public UniqueIDStrategy getUniqueIDStrategy() {
        return uniqueIDStrategy;
    }

}
