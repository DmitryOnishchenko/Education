package com.donishchenko.multi.booleansync;

public class PhysicsUpdateMethod extends UpdateMethod {
    public void update(int last, int toProcess) {
        for (; last <= toProcess; last++) {
            list.get(last).updatePhysics();

            updater.processed(last);
        }
    }
}
