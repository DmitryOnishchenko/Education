package com.donishchenko.multi.booleansync;

public class AiUpdateMethod extends UpdateMethod {
    public void update(int last, int toProcess) {
        for (; last <= toProcess; last++) {
            list.get(last).updateAi();

            updater.processed(last);
        }
    }
}
