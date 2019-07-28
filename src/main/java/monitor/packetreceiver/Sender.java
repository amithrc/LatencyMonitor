package main.java.monitor.packetreceiver;

import jpcap.PacketReceiver;
import jpcap.packet.Packet;

import java.util.Arrays;

public class Sender implements PacketReceiver {

    @Override
    public void receivePacket(Packet packet) {
        //System.out.println(packet.sec + "." + packet.usec);


        if (packet.data.length == 46)
        {
            //System.out.println(packet.data);

            byte[] slice = Arrays.copyOfRange(packet.data, 0, 4);
            System.out.println(slice);
        }

    }
}
