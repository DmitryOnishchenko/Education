package com.donishchenko.testgame.object.ai;

import com.donishchenko.testgame.object.GameObject;
import com.donishchenko.testgame.object.action.Action;

public abstract class Idea {

    public String name;
    public GameObject gameObject;
    public int priority;
    public Action action;

    public Idea(String name, GameObject gameObject, int priority) {
        this.name = name;
        this.gameObject = gameObject;
        this.priority = priority;
    }

    public abstract void init();

    /**
     * Return true if this idea can become the main. Services returns false
     * @return
     */
    public abstract boolean think();

}
