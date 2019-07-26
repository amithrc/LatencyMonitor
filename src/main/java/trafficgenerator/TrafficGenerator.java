package main.java.trafficgenerator;


import main.java.commandparser.Config;
import main.java.monitor.SetupInterface.SetupInterface;

import java.util.logging.Logger;

/**
 * This class generates the traffic artificially on a given interface
 * Keep pushing the traffic, A byte array puts sequence number in it
 */

public class TrafficGenerator implements Runnable {
    private Config config = null;
    private Logger logger = null;

    public TrafficGenerator(Config config) {
        this.config = config;
        logger = config.getLogger();
    }


    void sendTraffic() {

        SetupInterface senderInterface = new SetupInterface(config.getInterfaceSender(), config.getTimeStampType(), logger);

        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(i);

        }
    }


    @Override
    public void run() {
        sendTraffic();
    }
}


