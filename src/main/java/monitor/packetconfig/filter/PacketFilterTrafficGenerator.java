package main.java.monitor.packetconfig.filter;

import jpcap.packet.Packet;
import main.java.commandparser.Config;
import main.java.monitor.container.TimeStamp;
import main.java.monitor.packetconfig.PacketInfo;
import main.java.monitor.utils.ByteOperation;
import main.java.monitor.utils.Constants;

import java.util.Arrays;

public class PacketFilterTrafficGenerator extends PacketFilterBase {

    private Config config = null;
    private boolean isHw = false;

    public PacketFilterTrafficGenerator(Config config) {
        this.config = config;

        if (config.getTimeStampType() == Config.TimeStampType.HARDWARE_TIME_STAMP) {
            isHw = true;
        }
    }

    @Override
    public PacketInfo getPacketInfo(Packet packet) {

        byte[] uuidSlice = Arrays.copyOfRange(packet.data, 0, config.getUidLength());
        byte[] iidSlice = Arrays.copyOfRange(packet.data, config.getUidLength(), config.getUidLength() + 8);
        String uid = new String(uuidSlice);

        PacketInfo info = null;

        if (uid.equalsIgnoreCase(config.getUidpattern())) {
            long iid = ByteOperation.getInteger(iidSlice);
            long res = convertTimeUnit(isHw, config.getTimeUnit(), packet.sec, packet.usec);
            info = new PacketInfo(iid, new TimeStamp(packet.sec, packet.usec, res));
        }
        return info;
    }
}