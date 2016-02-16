package com.donishchenko.testgame.gamestate.test;


import com.donishchenko.testgame.config.GameConstants;
import com.donishchenko.testgame.gamestate.GameState;
import com.donishchenko.testgame.gamestate.GameStateManager;
import com.donishchenko.testgame.resources.GraphicsModel;
import com.donishchenko.testgame.resources.ResourceLoader;
import com.donishchenko.testgame.resources.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.donishchenko.testgame.resources.ImageUtils.createBufferedImage;
import static com.donishchenko.testgame.resources.ImageUtils.makeImageTranslucent;
import static com.donishchenko.testgame.resources.ImageUtils.toCompatibleImage;

public class TestGameState extends GameState {

    private BufferedImage backgroundLayer_0;
    private BufferedImage backgroundLayer_1;

    private int total = 10_000;
    private List<TestObject> testObjects;

    public TestGameState(GameStateManager gsm) {
        super(gsm);
        testObjects = new ArrayList<>();
    }

    @Override
    public void init() {
        Resources common = ResourceLoader.getResources("common");
        backgroundLayer_0 = common.get("backgroundLayer_0");
        backgroundLayer_1 = common.get("backgroundLayer_1");

        Resources resources1 = ResourceLoader.getResources("Human Soldier");
        Resources resources2 = ResourceLoader.getResources("Human Archer");
        Resources resources3 = ResourceLoader.getResources("Orc Soldier");
        Resources resources4 = ResourceLoader.getResources("Orc Archer");
        Resources resources5 = ResourceLoader.getResources("Orc Soldier Elite");

        GraphicsModel model1 = resources1.get("graphicsModel");
        GraphicsModel model2 = resources2.get("graphicsModel");
        GraphicsModel model3 = resources3.get("graphicsModel");
        GraphicsModel model4 = resources4.get("graphicsModel");
        GraphicsModel model5 = resources5.get("graphicsModel");

        List<BufferedImage> images = new ArrayList<>();
        images.addAll(Arrays.asList(model1.getFightSpritesRight()));
        images.addAll(Arrays.asList(model2.getFightSpritesRight()));
        images.addAll(Arrays.asList(model3.getFightSpritesRight()));
        images.addAll(Arrays.asList(model4.getFightSpritesRight()));
        images.addAll(Arrays.asList(model5.getFightSpritesRight()));

        Random r = new Random();
        for (int i = 0; i < total; i++) {
            testObjects.add(new TestObject(images.get(r.nextInt(40)), r.nextInt(GameConstants.DEFAULT_WIDTH), r.nextInt(GameConstants.DEFAULT_HEIGHT)));
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

    private final int rules[] = {
            AlphaComposite.DST,
            AlphaComposite.DST_ATOP,
            AlphaComposite.DST_OUT,
            AlphaComposite.SRC,
            AlphaComposite.SRC_ATOP,
            AlphaComposite.SRC_OUT,
            AlphaComposite.SRC_OVER
    };
    private BufferedImage translucent = toCompatibleImage(makeImageTranslucent(createBufferedImage(), 0));
    @Override
    public void render(Graphics2D g2) {
        Graphics2D g2test = (Graphics2D) translucent.getGraphics();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.DST_ATOP);
        g2test.setComposite(ac);
        g2test.drawImage(backgroundLayer_1, 0, 0, null);

//        for (TestObject obj : testObjects) {
//            obj.render(g2);
//        }
//
//        g2.drawImage(backgroundLayer_0, 0, 0, null);

        /* Info */
        g2test.setPaint(Color.WHITE);
        g2test.drawString("TestState | Objects: " + total, 900, 36);

        ac = AlphaComposite.getInstance(AlphaComposite.SRC);
        g2.setComposite(ac);
        g2.drawImage(translucent, 0, 0, null);
    }
}
