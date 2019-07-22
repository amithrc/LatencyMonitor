package main.java.monitor.packetreceiver;

import jpcap.PacketReceiver;
import jpcap.packet.Packet;

public class Receiver  implements PacketReceiver {
    @Override
    public void receivePacket(Packet packet) {
        System.out.println(packet.sec + "." + packet.usec);

    }
}
