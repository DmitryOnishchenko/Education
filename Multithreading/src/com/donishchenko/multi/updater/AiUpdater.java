package com.donishchenko.multi.updater;

import com.donishchenko.multi.Sync;
import com.donishchenko.multi.SyncMain;

public class AiUpdater extends Thread {
    @Override
    public void run() {
        // First thread
        while (true) {
            synchronized (Sync.getGameLoop()) {
                // Wait for new game loop
                try {
                    System.out.println("AI:: waiting for new game loop");
                    Sync.waitGameLoop();
                    System.out.println("\n======================\n");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Do my work
                System.out.println("AI:: do my work");
                int size = SyncMain.size();
                for (int i = 0; i < size; i++) {
                    SyncMain.list.get(i).updateAi();

                    if (i % Sync.UNITS == 0) {
                        Sync.aiProcessed = i;
                        synchronized (Sync.getAi()) {
                            System.out.println("AI:: notify Ai");
                            Sync.notifyAi();
                        }
                    }
                }

                System.out.println("AI:: all work is done. Processed: " + size);
                synchronized (Sync.getAi()) {
                    Sync.aiProcessed = size;
                    System.out.println("AI:: notify Ai");
                    Sync.notifyAi();
                }
            }

            try {
                sleep(Sync.SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
