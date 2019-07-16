package main.java.Monitor.SetupInterface;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import main.java.commandparser.Config;

import java.io.IOException;

/**
 * Setup the network Interface
 */
public class SetupInterface {

    private String interfaceName = null;
    private NetworkInterface networkInterface = null;
    private Config.TimeStampType type = null;


    public SetupInterface(String networkInterface, Config.TimeStampType type) {
        this.interfaceName = networkInterface;
        this.networkInterface = fetchInterface(interfaceName);
        this.type = type;

        if (this.networkInterface == null) {
            System.out.println("Could not find the network interface");
        }
    }

    private NetworkInterface fetchInterface(String interfaceName) {
        for (NetworkInterface inter : JpcapCaptor.getDeviceList()) {
            if (inter.name.equalsIgnoreCase(interfaceName))
                return inter;
        }
        return null;
    }

    public NetworkInterface getNetworkInterface() {
        return networkInterface;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public JpcapCaptor getCaptor() {
        boolean timestamptype = false;

        if (this.type == Config.TimeStampType.HARDWARE_TIME_STAMP) {
            timestamptype = true;
        }
        System.out.println("What is type " + timestamptype);
        JpcapCaptor captor = null;
        if (this.networkInterface != null) {

            try {
                captor = JpcapCaptor.openDevice(this.networkInterface, 10000,
                        true, 1, timestamptype);
            } catch (IOException e) {
                e.printStackTrace();
            }
            captor.setNonBlockingMode(false);

        } else {
            System.out.println("Failed to open the interface");
            System.exit(-1);
        }
        return captor;
    }
}
