package com.donishchenko.multi.booleansync;

import com.donishchenko.multi.SyncMain;
import com.donishchenko.multi.updater.SyncHelper;

import javax.swing.*;

public class AbstractUpdater extends Thread {
    private String name;
    private boolean prime;
    private volatile boolean done;
    private volatile int toProcess;
    private AbstractUpdater nextUpdater;

    public AbstractUpdater(String name, AbstractUpdater next, boolean prime) {
        this.name = name;
        this.nextUpdater = next;
        this.prime = prime;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (toProcess == 0)

                if (prime) {
                    System.out.println("\n\t===== New Loop started =====");
                }

                int size = SyncMain.size();
                if (prime) {
                    toProcess = size;
                    for (int i = 0; i < toProcess; i++) {
                        // Do my work
                        SyncMain.list.get(i).updateAi();

                        // If i did part of work
                        if (nextUpdater != null && i % SyncHelper.PACKAGE == 0) {
                            nextUpdater.toProcess = i;
                        }
                    }
                } else {
                    while (toProcess == 0) {
                        sleep(100);
                    }

                    for (int i = 0; i < size;) {
                        // Do my work
                        for (; i < toProcess; i++) {
                            SyncMain.list.get(i).updateAction();
                        }

                        // If i did part of work
                        if (nextUpdater != null) {
                            nextUpdater.toProcess = i;
                        }
                    }
                }

                done = true;
                System.out.println(name + ":: ENDED. toProcess: " + toProcess);
                System.out.println("\tGameObject: " + SyncMain.list.get(0).ai + " | " + SyncMain.list.get(0).action);
                if (nextUpdater != null) {
                    nextUpdater.toProcess = size;
                } else {
                    SyncHelper.gameLoopDone = true;
                    toProcess = 0;
                }

                if (prime) {
                    sleep(3000);
                } else {
                    sleep(100);
                }
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
