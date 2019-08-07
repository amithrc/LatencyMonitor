package main.java.monitor.packetconfig;

import jpcap.packet.Packet;
import main.java.commandparser.Config;

/**
 * This class implements parsing the packet which is passed in.
 * This returns for each packet that is of interest, returns its TimeStamp
 * and the ID
 */

public class PacketFilter {



    private Config config = null;
    boolean isHardWareTimeStamp = (config.getTimeStampType() == Config.TimeStampType.HARDWARE_TIME_STAMP) ? true : false;

    public PacketFilter(Config config) {
        this.config = config;
    }


    public PacketInfo getPacketInfo(Packet packet) {



        return null;
    }
}
