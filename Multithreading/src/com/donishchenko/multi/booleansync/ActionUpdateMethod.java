package com.donishchenko.multi.booleansync;

public class ActionUpdateMethod extends UpdateMethod {
    public void update(int last, int toProcess) {
        for (; last <= toProcess; last++) {
            list.get(last).updateAction();

            updater.processed(last);
        }
    }
}
