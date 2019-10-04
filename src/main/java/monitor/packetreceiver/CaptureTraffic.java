package main.java.monitor.packetreceiver;

import jpcap.PacketReceiver;
import jpcap.JpcapPacket;
import main.java.commandparser.Config;
import main.java.monitor.container.TimeStamp;
import main.java.monitor.packetconfig.filter.PacketFilterBase;
import main.java.monitor.packetconfig.PacketInfo;
import main.java.monitor.utils.WriteCSV;


public class CaptureTraffic implements PacketReceiver {


    private Config config = null;
    private PacketFilterBase filter = null;
    private WriteCSV writeCSV = null;


    public CaptureTraffic(Config config, PacketFilterBase filter) {
        this.config = config;
        this.filter = filter;
        String[] header = {"packetID", "sec", "usec", "unit"};
        writeCSV = new WriteCSV(header, config.getStatsfile());
    }

    @Override
    public void receivePacket(JpcapPacket packet) {
        PacketInfo info = filter.getPacketInfo(packet);
        if (info != null) {
            TimeStamp t = info.getTimeStamp();
            System.out.println("Packet ID: " + info.getPacketID() + " Packet sec: " + t.getSeconds() + " Packet usec: " + t.getMicroNanoseconds() + " Packet in " + config.getUnitString() + ": " + t.getResultTimeUnit());

            String id = String.valueOf(info.getPacketID());
            String sec = String.valueOf(t.getSeconds());
            String usec = String.valueOf(t.getMicroNanoseconds());
            String unit = config.getUnitString();

            writeCSV.writeLine(new String[]{id, sec, usec, unit});


        }
    }
}
