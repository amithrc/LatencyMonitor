package main.java.monitor.helper;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import main.java.commandparser.Config;

public class Helper {

    private Config config = null;

    public Helper(Config config)
    {
        this.config =config;
    }

    public void listInterface()
    {   System.out.println("-------------------------------------------Network Interface Details-----------------------------------------------");

        for (NetworkInterface inter : JpcapCaptor.getDeviceList()) {
            System.out.println("Interface name:\t"+ inter.name + "\t"+ "Description:\t"+ inter.description +"\t");

        }
        System.out.println("--------------------------------------------------------------------------------------------------------------------");
    }
}
