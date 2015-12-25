package com.donishchenko.testgame.gamestate.test;

import com.donishchenko.testgame.object.GameObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.VolatileImage;
import java.util.Random;

public class TestObject extends GameObject<TestObject> {

    private final VolatileImage image;

    private float x;
    private float y;
    private Color color;
    private float speedX;
    private float speedY;
    private int width;
    private int height;

    public TestObject(VolatileImage volatileImage, float x, float y) {
        this.image = volatileImage;
        this.x = x;
        this.y = y;
        Random r = new Random();
        this.color = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256), r.nextInt(256));
        speedX = r.nextFloat() * 3;
        speedY = r.nextFloat() * 3;
        width = 10 + r.nextInt() % 100;
        height = 10 + r.nextInt() % 100;
    }

    @Override
    public void init() {

    }

    @Override
    public void updateInput(KeyEvent event) {

    }

    @Override
    public void updateAi() {
        if (x > 1280 || x < 0) {
            speedX = -speedX;
        }
        if (y > 720 || y < 0) {
            speedY = -speedY;
        }
    }

    @Override
    public void updateAction() {
        x += speedX;
        y += speedY;
    }

    @Override
    public void updatePhysics() {

    }

    @Override
    public void updateGraphics() {

    }

    @Override
    public void render(Graphics2D g2) {
        g2.setPaint(color);
        g2.fillRect((int) x, (int) y, width, height);
    }

    @Override
    public void switchState() {

    }

    @Override
    public int compareTo(TestObject o) {
        return 0;
    }
}
