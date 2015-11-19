package multitest.delayqueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayObjectTest implements Delayed {
    private static int ID = 0;
    private int id;

    private String message;
    private long startTime;

    public DelayObjectTest(String message, long delay) {
        this.id = ++ID;
        this.message = message;
        this.startTime = System.currentTimeMillis() + delay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = startTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);

    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }
}
