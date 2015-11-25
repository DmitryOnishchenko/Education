package com.donishchenko.multi;

import com.donishchenko.multi.updater.ActionUpdater;
import com.donishchenko.multi.updater.AiUpdater;

public class Daemon extends Thread {

    private AiUpdater aiUpdater;
    private ActionUpdater actionUpdater;

    public Daemon(AiUpdater aiUpdater, ActionUpdater actionUpdater) {
        this.aiUpdater = aiUpdater;
        this.actionUpdater = actionUpdater;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            if (actionUpdater.done) {
                if (aiUpdater.getState() == State.WAITING) {
                    actionUpdater.done = false;
                    synchronized (Sync.getGameLoop()) {
                        Sync.notifyGameLoop();
                    }
                }
            }

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
