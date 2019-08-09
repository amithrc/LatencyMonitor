package main.java.monitor.container;


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
