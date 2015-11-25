package com.donishchenko.multi;

public class Producer extends Thread {

    private Object sync;
    private Object main;

    public Producer(Object sync, Object main) {
        this.sync = sync;
        this.main = main;
    }

    @Override
    public void run() {
        int id = 1;
        while (true) {
            synchronized (sync) {
//                System.out.println("Producer:: add GameObject with id " + id);

                GameObject gameObject = new GameObject(id++);
                SyncMain.list.add(gameObject);

                if (SyncMain.list.size() % 50 == 0) {
                    System.out.println("Producer:: notify");
                    sync.notify();
                }

                try {
                    sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            synchronized (main) {
                if (SyncMain.list.size() == 200) {
                    System.out.println("Producer:: FULL WORK. waiting");
                    try {
                        main.wait();

                        System.out.println("Producer:: clear all");
                        SyncMain.list.clear();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
