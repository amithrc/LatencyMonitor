package main.java.monitor.packetreceiver;

import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import main.java.commandparser.Config;
import main.java.monitor.helper.ByteOperation;


import java.util.Arrays;

public class Sender implements PacketReceiver {

    private Config config = null;

    public Sender(Config config) {
        this.config = config;

    }

    @Override
    public void receivePacket(Packet packet) {


        byte[] uuidSlice = Arrays.copyOfRange(packet.data, 0, config.getUidLength());
        byte[] iidSlice = Arrays.copyOfRange(packet.data, config.getUidLength(), config.getUidLength() + 4);

        //System.out.println("UUID length = " + uuidSlice.length + " IID length = " + iidSlice.length);

        //System.out.println(iidSlice.length);
        String uid = new String(uuidSlice);


        if (uid.equalsIgnoreCase(config.getUidpattern())) {
            System.out.println("ID matched !!!!!!!!!!!! " + uid);
            int iid = ByteOperation.getInteger(iidSlice);
            System.out.println("UID " + uid + "IID = "+ iid);

        }


    }
}
