package main.java.monitor.packetreceiver;

import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import main.java.commandparser.Config;
import main.java.monitor.container.TimeStamp;
import main.java.monitor.packetconfig.PacketConfig;
import main.java.monitor.packetconfig.filter.PacketFilterBase;
import main.java.monitor.packetconfig.PacketInfo;
import main.java.monitor.storage.Storage;
import main.java.monitor.utils.WriteCSV;



public class Receiver implements PacketReceiver {


    private Config config = null;

    private Storage table = null;
    private PacketFilterBase filter = null;
    private WriteCSV csvWriter = null;

    public Receiver(Config config, PacketConfig packetConfig) {
        this.config = config;
        this.table = packetConfig.getStorage();
        this.filter = packetConfig.getPacketFilter();


        String[] header = null;

        if (config.isStdOutEnabled()) {
            header = new String[]{"packetid", "latency", "unit", "STI/sec", "STI/subsec", "STI/convertedunit", "RTI/sec", "RTI/subsec", "RTI/convertedunit"};

        } else {
            header = new String[]{"packetid", "latency", "unit"};
        }
        csvWriter = new WriteCSV(header, config.getStatsfile());
    }

    @Override
    public void receivePacket(Packet packet) {

        PacketInfo packetInfo = filter.getPacketInfo(packet);
        if (packetInfo != null) {

            long packetID = packetInfo.getPacketID();
            TimeStamp T2 = packetInfo.getTimeStamp();


            if (table.hasPacket(packetInfo.getPacketID())) {
                TimeStamp T1 = table.getTimeStamp(packetID).getT1();

                String[] data = null;
                String pid = String.valueOf(packetID);
                String latency = String.valueOf((T2.getResultTimeUnit() - T1.getResultTimeUnit()));
                String unit = config.getUnitString();


                if (config.isVerboseEnabled()) {
                    data = new String[]{pid, latency, unit};

                } else {
                    String stisec = String.valueOf(T1.getSeconds());
                    String stisubsec = String.valueOf(T1.getMicroNanoseconds());
                    String sticonverted = String.valueOf(T1.getResultTimeUnit());

                    String rtisec = String.valueOf(T2.getSeconds());
                    String rtisubsec = String.valueOf(T2.getMicroNanoseconds());
                    String rticonverted = String.valueOf(T2.getResultTimeUnit());

                    data = new String[]{pid, latency, unit, stisec, stisubsec, sticonverted, rtisec, rtisubsec, rticonverted};
                }
                csvWriter.writeLine(data);
                table.removePacket(packetID);

                if (config.isStdOutEnabled()) {
                    System.out.println("Receiver Interface/ Packet ID: " + pid + " RTT:" + latency + " " + config.getUnitString());
                }
            } else {
                table.addPacket(packetID, T2, false);
            }
        }
    }
}
