package main.java.monitor.packetreceiver;

import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import main.java.commandparser.Config;

import java.util.Arrays;
import java.util.Base64;

public class Sender implements PacketReceiver {

    private Config config = null;

    public Sender(Config config) {
        this.config = config;

    }

    @Override
    public void receivePacket(Packet packet) {


        byte[] slice = Arrays.copyOfRange(packet.data, 0, config.getUidLength());
        System.out.println(slice.length);
        String uid = new String(slice);
        System.out.println("UID " + uid);
        if (uid.equalsIgnoreCase(config.getUidpattern())) {
            System.out.println("ID matched !!!!!!!!!!!!" + uid);

        }


    }
}
