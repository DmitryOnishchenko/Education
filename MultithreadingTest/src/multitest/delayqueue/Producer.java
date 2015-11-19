package multitest.delayqueue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class Producer extends Thread {
    private DelayQueue<DelayObjectTest> q;

    public Producer(DelayQueue<DelayObjectTest> q) {
        this.q = q;
    }

    @Override
    public void run() {
        while (true) {
            DelayObjectTest obj = new DelayObjectTest("Producer:: Test message", 3000);
            q.add(obj);
            System.out.println("Producer:: Added new object");

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
