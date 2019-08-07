package main.java.monitor.container;


public class TimeStamp {

    private long seconds;
    private long microNanoseconds;
    private long nanosecond = 0L;

    public TimeStamp(long seconds, long microNanoseconds, long nanosecond) {
        this.seconds = seconds;
        this.microNanoseconds = microNanoseconds;
        this.nanosecond = nanosecond;
    }

    public long getSeconds() {
        return seconds;
    }

    public long getMicroNanoseconds() {
        return microNanoseconds;
    }

    public long getNanosecond() {
        return nanosecond;
    }
}
