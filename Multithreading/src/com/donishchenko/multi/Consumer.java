package com.donishchenko.multi;

public class Consumer extends Thread {

    private int processed = 0;
    private Object sync;
    private Object main;

    public Consumer(Object sync, Object main) {
        this.sync = sync;
        this.main = main;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (sync) {
                try {
                    System.out.println("Consumer:: waiting");
                    sync.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                System.out.println("Consumer:: start process");

                int size = SyncMain.list.size();
                for (int i = processed; i < size; i++) {
                    GameObject gameObject = SyncMain.list.get(i);
                    gameObject.ai += 1;
//                    System.out.println("Consumer:: process GameObject " + gameObject);
                }
                System.out.println("Consumer:: processed " + (size - processed));
                processed = size;
            }

            synchronized (main) {
                if (SyncMain.list.size() == 200) {
                    System.out.println("Consumer:: do all work");
                    main.notify();
                }
            }
        }
    }
}
