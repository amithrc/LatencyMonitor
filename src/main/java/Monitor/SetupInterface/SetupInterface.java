package main.java.Monitor.SetupInterface;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

/**
 * Setup the network Interface
 */
public class SetupInterface {

    private String interfaceName = null;
    private NetworkInterface networkInterface = null;


    public SetupInterface(String networkInterface) {
        this.interfaceName = networkInterface;
        this.networkInterface = fetchInterface(interfaceName);

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
}
