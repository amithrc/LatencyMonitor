package main.java.monitor.stratergy;

import jpcap.packet.Packet;

public class Strategy1 extends UniqueIDStrategy {


    @Override
    public long getPacketID(Packet packet) {
        return 0;
    }
}
