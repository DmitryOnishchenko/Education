package com.donishchenko.testgame.object;

import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class GameObject<T> implements Comparable<T> {
    boolean delete = false;

    public abstract void init();
    public abstract void updateInput(KeyEvent event);
    public abstract void updateAi();
    public abstract void updateAction();
    public abstract void updatePhysics();
    public abstract void updateGraphics();
    public abstract void render(Graphics2D g2);
    public abstract void switchState();

}
