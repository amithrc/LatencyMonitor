package main.java.monitor.stratergy.uniqueid;

import jpcap.packet.Packet;
import main.java.commandparser.Config;
import main.java.monitor.helper.ByteOperation;

import java.util.Arrays;

public class Strategy1 extends UniqueIDStrategy<Long> {

    private Config config = null;

    public Strategy1(Config config) {
        this.config = config;
    }

    @Override
    public Long getPacketID(Packet packet) {
        byte[] uuidSlice = Arrays.copyOfRange(packet.data, 0, config.getUidLength());
        byte[] iidSlice = Arrays.copyOfRange(packet.data, config.getUidLength(), config.getUidLength() + 8);
        String uid = new String(uuidSlice);
        long iid = 0;
        if (uid.equalsIgnoreCase(config.getUidpattern())) {
            return iid = ByteOperation.getInteger(iidSlice);
        }
        return (long) -1;
    }
}
