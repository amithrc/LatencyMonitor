package main.java.monitor.packetreceiver;

import jpcap.PacketReceiver;
import jpcap.JpcapPacket;
import main.java.commandparser.Config;
import main.java.monitor.container.TimeStamp;
import main.java.monitor.packetconfig.PacketConfig;
import main.java.monitor.packetconfig.filter.PacketFilterBase;
import main.java.monitor.packetconfig.PacketInfo;
import main.java.monitor.storage.Storage;
import main.java.monitor.utils.WriteCSV;


/**
 * Captures all the traffic received on the sender interface, uses one of the packet filter
 * to capture the interested packet.
 *
 * @author Amith
 */
public class Sender implements PacketReceiver {

    private Config config = null;

    private Storage table = null;
    private PacketFilterBase filter = null;
    private WriteCSV csvWriter = null;


    public Sender(PacketConfig packetConfig) {
        this.config = packetConfig.getConfig();
        this.table = packetConfig.getStorage();
        this.filter = packetConfig.getPacketFilter();
        csvWriter = packetConfig.getCsvWriter();
    }

    /**
     * This methods loops through all the packets that receives on the interface
     * and filters the interested packet. Calling addPacket will add the timestamp T1 for the packet
     *
     * @param packet - Gets each packet out of the interface
     */
    @Override
    public void receivePacket(JpcapPacket packet) {

        PacketInfo packetInfo = filter.getPacketInfo(packet);

        if (packetInfo != null) {
            long packetID = packetInfo.getPacketID();
            TimeStamp T1 = packetInfo.getTimeStamp();

            if (table.hasPacket(packetID)) {
                TimeStamp T2 = table.getTimeStamp(packetID).getT2();

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
                    data = new String[]{pid, latency, unit, stisec, stisubsec, sticonverted, rtisec, rtisubsec, rticonverted, config.getHeaderType().toString(),framesize};
                }
                csvWriter.writeLine(data);
                table.removePacket(packetID);

                if (config.isStdOutEnabled()) {
                    System.out.println("sender Interface/ Packet ID: " + pid + " RTT:" + latency + " " + config.getUnitString());
                }

            } else {
                table.addPacket(packetID, T1, true);
            }
        }
    }
}
