package com.donishchenko.multi;

public class GameObject {
    public int id;
    public int ai = 0;
    public int action = 0;
    public int physics = 0;
    public int graphics = 0;

    public GameObject(int id) {
        this.id = id;
    }

    public void updateAi() {
        ai++;
    }

    public void updateAction() {
        action++;
    }

    public void updatePhysics() {
        physics++;
    }

    public void updateGraphics() {
        graphics++;
    }

    @Override
    public String toString() {
        return "GameObject{ai=" + ai +
                "|action=" + action +
                "|physics=" + physics +
                "|graphics=" + graphics;
    }
}