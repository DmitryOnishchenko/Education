package com.donishchenko.multi.booleansync;

import com.donishchenko.multi.GameObject;
import com.donishchenko.multi.SyncMain;

import java.util.List;

public abstract class UpdateMethod {
    protected List<GameObject> list = SyncMain.list;
    protected Updater updater;

    public abstract void update(int last, int toProcess);
}
