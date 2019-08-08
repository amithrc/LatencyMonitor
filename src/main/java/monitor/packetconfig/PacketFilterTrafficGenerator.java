package main.java.monitor.packetconfig;

import jpcap.packet.Packet;
import main.java.commandparser.Config;
import main.java.monitor.container.TimeStamp;
import main.java.monitor.utils.ByteOperation;

import java.util.Arrays;

public class PacketFilterTrafficGenerator extends PacketFilterBase {

    private Config config = null;
    //boolean isHardWareTimeStamp = (config.getTimeStampType() == Config.TimeStampType.HARDWARE_TIME_STAMP) ? true : false;

    public PacketFilterTrafficGenerator(Config config) {
        this.config = config;
    }


    @Override
    public PacketInfo getPacketInfo(Packet packet) {

        byte[] uuidSlice = Arrays.copyOfRange(packet.data, 0, config.getUidLength());
        byte[] iidSlice = Arrays.copyOfRange(packet.data, config.getUidLength(), config.getUidLength() + 8);
        String uid = new String(uuidSlice);

        PacketInfo info = null;
        if (uid.equalsIgnoreCase(config.getUidpattern())) {
            long iid = ByteOperation.getInteger(iidSlice);
            info = new PacketInfo(iid, new TimeStamp(packet.sec, packet.usec, 0L));
        }
        return info;
    }
}
