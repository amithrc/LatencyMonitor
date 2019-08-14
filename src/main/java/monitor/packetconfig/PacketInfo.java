package main.java.monitor.packetconfig;

import main.java.monitor.container.TimeStamp;


/**
 * For packet, it saves the packedID  and a timestamp, the result unit will have the converted timestamp
 * Example, ms, sec, ns, us
 *
 * @author Amith
 */
public class PacketInfo {

    private Long packetID = 0L;
    private TimeStamp timeStamp = null;

    public PacketInfo(Long packetID, TimeStamp timeStamp) {
        this.packetID = packetID;
        this.timeStamp = timeStamp;
    }

    public Long getPacketID() {
        return packetID;
    }

    public TimeStamp getTimeStamp() {
        return timeStamp;
    }
}
