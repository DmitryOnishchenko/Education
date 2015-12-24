package com.donishchenko.testgame.gamestate.test;


import com.donishchenko.testgame.Application;
import com.donishchenko.testgame.gamestate.GameState;
import com.donishchenko.testgame.gamestate.GameStateManager;
import com.donishchenko.testgame.utils.ImageUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestGameState extends GameState {

    private List<TestObject> testObjects;

    public TestGameState(GameStateManager gsm) {
        super(gsm);
        testObjects = new ArrayList<>();
    }

    @Override
    public void init() {
        BufferedImage sprite = ImageUtils.loadImage(Application.class, "/units/humans/soldier/knight_move.png");
        sprite = ImageUtils.toCompatibleImage(sprite);
        VolatileImage volatileImage = ImageUtils.convertToVolatileImage(sprite);

        Random r = new Random();
        for (int i = 0; i < 3_000; i++) {
            testObjects.add(new TestObject(volatileImage, r.nextInt(1000), r.nextInt(720)));
        }
    }

    @Override
    public void processInput(KeyEvent event) {

    }

    @Override
    public void update() {
        for (TestObject obj : testObjects) {
            obj.updateAi();
            obj.updateAction();
            obj.updatePhysics();
            obj.updateGraphics();
        }
    }

    @Override
    public void render(Graphics2D g2) {
        for (TestObject obj : testObjects) {
            obj.render(g2);
        }
    }
}
