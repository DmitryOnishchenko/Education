package multitest.delayqueue;

import java.util.concurrent.DelayQueue;

public class Consumer extends Thread {
    private DelayQueue<DelayObjectTest> q;

    public Consumer(DelayQueue<DelayObjectTest> q) {
        this.q = q;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("\tConsumer:: Try to take");
                DelayObjectTest obj = q.take();
                System.out.println("\tConsumer:: Takes object from queue: " + obj.getMessage());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}
