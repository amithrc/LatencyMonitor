package main.java.monitor.packetreceiver;

import jpcap.PacketReceiver;
import jpcap.packet.Packet;

public class Sender implements PacketReceiver {

    @Override
    public void receivePacket(Packet packet) {
        System.out.println(packet.sec + "." + packet.usec);
    }
}
