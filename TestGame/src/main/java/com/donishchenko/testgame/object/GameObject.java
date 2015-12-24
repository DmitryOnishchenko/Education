package com.donishchenko.testgame.object;

import java.awt.*;
import java.awt.event.KeyEvent;

public interface GameObject<T> extends Comparable<T> {
    boolean delete = false;

    void init();
    void updateInput(KeyEvent event);
    void updateAi();
    void updateAction();
    void updatePhysics();
    void updateGraphics();
    void render(Graphics2D graphics, double deltaTime);
    void switchState();

}
