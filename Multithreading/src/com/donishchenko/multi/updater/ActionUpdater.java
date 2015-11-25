package com.donishchenko.multi.updater;

import com.donishchenko.multi.Sync;
import com.donishchenko.multi.SyncMain;

public class ActionUpdater extends Thread {

    public volatile boolean done = false;

    @Override
    public void run() {
        while (true) {
            synchronized (Sync.getAi()) {
                // Do my work
                int size = SyncMain.size();
                for (int i = 0; i < size; i++) {
                    // Wait for Ai
                    try {
                        System.out.println("Action:: wait for Ai");
                        Sync.waitAi();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Action:: do my work");
                    for ( ; i < Sync.aiProcessed; i++) {
                        SyncMain.list.get(i).updateAction();
                    }
                }

                done = true;
                System.out.println("Action:: all work is done. Processed: " + size);
            }

            try {
                sleep(Sync.SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
