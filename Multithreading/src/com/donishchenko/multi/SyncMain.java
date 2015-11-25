package com.donishchenko.multi;

import com.donishchenko.multi.booleansync.ActionUpdateMethod;
import com.donishchenko.multi.booleansync.AiUpdateMethod;
import com.donishchenko.multi.booleansync.PhysicsUpdateMethod;
import com.donishchenko.multi.booleansync.Updater;

import java.util.ArrayList;

public class SyncMain {

    public static final ArrayList<GameObject> list = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Fill array");
        for (int i = 0, id = 1; i < 100; i++, id++) {
            GameObject gameObject = new GameObject(id);
            list.add(gameObject);
        }

        System.out.println("Create and start threads\n");
//        AiUpdater aiUpdater = new AiUpdater();
//        aiUpdater.start();
//
//        ActionUpdater actionUpdater = new ActionUpdater();
//        actionUpdater.start();
//
//        new Daemon(aiUpdater, actionUpdater).start();
//
//        // Wait some time
//        Thread.currentThread().sleep(2000);
//
//        System.out.println("Main:: notify GameLoop\n");
//        synchronized (Sync.getGameLoop()) {
//            Sync.notifyGameLoop();
//        }

        // Second test: with boolean flag sync
        Updater prime = new Updater("PRIME", null, new AiUpdateMethod(), true);
        Updater second = new Updater("SECOND", null, new ActionUpdateMethod(), false);
        Updater third = new Updater("THIRD", null, new PhysicsUpdateMethod(), false);

        prime.setNextUpdater(second);
        second.setNextUpdater(third);
        third.setNextUpdater(prime);

        prime.start();
        second.start();
        third.start();

        Thread.currentThread().sleep(2000);
        prime.setToProcess(size() - 1);

        while (true) {
            System.out.println(list.get(0));
            Thread.currentThread().sleep(1000);
        }
    }

    public static int size() {
        return list.size();
    }
}