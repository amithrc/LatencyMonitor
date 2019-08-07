package main.java.monitor.utils;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import main.java.commandparser.Config;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Helper {

    private Config config = null;
    private Logger logger = null;

    public Helper(Config config) {
        this.config = config;
        this.logger = config.getLogger();
    }

    public void listInterface() {
        System.out.println("-------------------------------------------Network Interface Details-----------------------------------------------");

        for (NetworkInterface inter : JpcapCaptor.getDeviceList()) {
            logger.log(Level.FINEST,"Interface name:\t" + inter.name + "\t" + "Description:\t" + inter.description + "\t");
            System.out.println("Interface name:\t" + inter.name + "\t" + "Description:\t" + inter.description + "\t");
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------------");
    }
}
