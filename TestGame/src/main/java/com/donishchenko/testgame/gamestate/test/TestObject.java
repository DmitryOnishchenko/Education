package com.donishchenko.testgame.gamestate.test;

import com.donishchenko.testgame.object.GameObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.VolatileImage;

public class TestObject extends GameObject<TestObject> {

    private final VolatileImage image;
    private int x;
    private int y;

    public TestObject(VolatileImage volatileImage, int x, int y) {
        this.image = volatileImage;
        this.x = x;
        this.y = y;
    }

    @Override
    public void init() {

    }

    @Override
    public void updateInput(KeyEvent event) {

    }

    @Override
    public void updateAi() {

    }

    @Override
    public void updateAction() {

    }

    @Override
    public void updatePhysics() {

    }

    @Override
    public void updateGraphics() {

    }

    @Override
    public void render(Graphics2D g2) {
        g2.drawImage(image, x, y, null);
    }

    @Override
    public void switchState() {

    }

    @Override
    public int compareTo(TestObject o) {
        return 0;
    }
}
