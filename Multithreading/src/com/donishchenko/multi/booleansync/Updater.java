package com.donishchenko.multi.booleansync;

import com.donishchenko.multi.SyncMain;

import javax.swing.*;

public class Updater extends Thread {
    private String name;
    private boolean prime;

    private volatile int toProcess;
    private volatile int last;

    private Updater nextUpdater;
    private UpdateMethod updateMethod;

    public Updater(String name, Updater next, UpdateMethod updateMethod, boolean prime) {
        this.name = name;
        this.nextUpdater = next;
        this.updateMethod = updateMethod;
        updateMethod.updater = this;
        this.prime = prime;
    }

    public Updater getNextUpdater() {
        return nextUpdater;
    }

    public void setNextUpdater(Updater nextUpdater) {
        this.nextUpdater = nextUpdater;
    }

    public void setToProcess(int toProcess) {
        this.toProcess = toProcess;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int sleepFor = 1000;
                int needToProcess = SyncMain.size() - 1;

                if (prime) {
                    while (toProcess != needToProcess) {
//                        System.out.println(name + ":: waiting");
                        sleep(sleepFor);
                    }
                    System.out.println("\n\t===== New game Loop =====\n");
                } else {
                    while (last == toProcess) {
//                        System.out.println(name + ":: waiting");
                        sleep(sleepFor);
                    }
                }

//                System.out.println(name + ":: start work");
                updateMethod.update(last, toProcess);

                if (last == needToProcess) {
                    toProcess = 0;
                    last = 0;
                    System.out.println(name + ":: done");
                }

                sleep(sleepFor);
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

    public void processed(int last) {
        this.last = last;
        nextUpdater.toProcess = last;
    }
}
