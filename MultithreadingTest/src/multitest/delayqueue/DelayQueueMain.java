package multitest.delayqueue;

import java.util.concurrent.DelayQueue;

public class DelayQueueMain {
    public static void main(String[] args) {
        DelayQueue<DelayObjectTest> messages = new DelayQueue<>();

        Consumer consumer = new Consumer(messages);
        consumer.start();

        Producer producer = new Producer(messages);
        producer.start();
    }
}
