package main.java.monitor.packetconfig;

import main.java.monitor.container.TimeStamp;

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
