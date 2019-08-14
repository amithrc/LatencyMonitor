package main.java.monitor.container;

/**
 * This class holds the timestamp of the packet.
 * For each packet there will be a timestamp, the resultTimeUnit field holds the converted value based on the User input. Either ms,us,ns
 *
 * @author Amith
 */
public class TimeStamp {

    private long seconds;
    private long microNanoseconds;
    private long resultTimeUnit = 0L;

    public TimeStamp(long seconds, long microNanoseconds, long resultTimeUnit) {
        this.seconds = seconds;
        this.microNanoseconds = microNanoseconds;
        this.resultTimeUnit = resultTimeUnit;
    }

    public long getSeconds() {
        return seconds;
    }

    public long getMicroNanoseconds() {
        return microNanoseconds;
    }

    public long getResultTimeUnit() {
        return resultTimeUnit;
    }
}
