package main.java.monitor.container;


/**
 * Time Stamp Container which contains T1 and T2 and the calculated difference
 * @author Amith
 * @version  8/11/2019
 */
public class TimeStampContainer {

    private TimeStamp T1 = null;
    private TimeStamp T2 = null;
    private long rtt = 0L;

    public long getRtt() {
        return rtt;
    }

    public TimeStamp getT1() {
        return T1;
    }

    public TimeStamp getT2() {
        return T2;
    }

    public void setT1(TimeStamp t1) {
        T1 = t1;
    }

    public void setT2(TimeStamp t2) {
        T2 = t2;
    }

    public void setRtt(long rtt) {
        this.rtt = rtt;
    }
}
