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

    public Receiver(PacketConfig packetConfig) {
        this.config = packetConfig.getConfig();
        this.table = packetConfig.getStorage();
        this.filter = packetConfig.getPacketFilter();
        csvWriter = packetConfig.getCsvWriter();
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


                if (!config.isVerboseEnabled()) {
                    data = new String[]{pid, latency, unit};

                } else {
                    String stisec = String.valueOf(T1.getSeconds());
                    String stisubsec = String.valueOf(T1.getMicroNanoseconds());
                    String sticonverted = String.valueOf(T1.getResultTimeUnit());

                    String rtisec = String.valueOf(T2.getSeconds());
                    String rtisubsec = String.valueOf(T2.getMicroNanoseconds());
                    String rticonverted = String.valueOf(T2.getResultTimeUnit());
                    String framesize = String.valueOf(packet.len);

                    data = new String[]{pid, latency, unit, stisec, stisubsec, sticonverted, rtisec, rtisubsec, rticonverted, config.getHeaderType().toString(), framesize};
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
