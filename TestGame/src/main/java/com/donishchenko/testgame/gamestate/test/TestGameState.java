package com.donishchenko.testgame.gamestate.test;


import com.donishchenko.testgame.Application;
import com.donishchenko.testgame.config.GameConstants;
import com.donishchenko.testgame.gamestate.GameState;
import com.donishchenko.testgame.gamestate.GameStateManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.VolatileImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.donishchenko.testgame.utils.ImageUtils.convertToVolatileImage;
import static com.donishchenko.testgame.utils.ImageUtils.loadImage;

public class TestGameState extends GameState {

    private VolatileImage backgroundFloor_0;
    private VolatileImage backgroundFloor_1;

    private List<TestObject> testObjects;

    public TestGameState(GameStateManager gsm) {
        super(gsm);
        testObjects = new ArrayList<>();
    }

    @Override
    public void init() {
        backgroundFloor_0 = convertToVolatileImage(loadImage(Application.class, "/background/map_ideal_0.png"));
        backgroundFloor_1 = convertToVolatileImage(loadImage(Application.class, "/background/map_ideal_1.png"));

        Random r = new Random();
        for (int i = 0; i < 5_000; i++) {
            testObjects.add(new TestObject(null, r.nextInt(GameConstants.DEFAULT_WIDTH), r.nextInt(GameConstants.DEFAULT_HEIGHT)));
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
//        g2.drawImage(backgroundFloor_0, 0, 0, null);

        for (TestObject obj : testObjects) {
            obj.render(g2);
        }

//        g2.drawImage(backgroundFloor_1, 0, 0, null);
    }
}
