package main.java;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import main.java.commandparser.CommandParser;
import main.java.runnner.Monitor;
import main.java.runnner.ProgramRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class projectMain
{
    static class RunThing implements Runnable {
        @Override
        public void run() {
            //do thing
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {

//        AtomicReference<NetworkInterface> intf = new AtomicReference<>();
//
//
//        Arrays.stream(JpcapCaptor.getDeviceList()).forEach(i -> {
//            if(i.name.equals("wlp1s0")) {
//                intf.set(i);
//            }
//        });
//
//        if(intf.get() == null)
//            throw new IllegalStateException();
//
//        JpcapCaptor captor = JpcapCaptor.openDevice(intf.get(), 10000,
//                true, 1, false);
//        captor.setNonBlockingMode(false);
//
//        final PacketReceiver pr = pack -> {
//            //Do something with the packet
//            //pack.sec seconds portion
//            //pack.usec microsec if software ts, nano if hardware
//            System.out.println(pack.sec + "." + pack.usec);
//            System.out.println(pack.data.length);
//        };
//
//        Thread t = new Thread(() -> captor.loopPacket(
//                -1, //Number of packets to capture, -1 if infinite
//                pr //Function to run for each received packet
//        ));
//
//        Thread t1 = new Thread(new RunThing());
//
//
//
//        t.setName("JpcapRcvThread");
//        t.start();
//        t.join();

        CommandParser parser = new CommandParser(args);
        ProgramRunner programRunner = new Monitor(parser);
        programRunner.run();

    }
}