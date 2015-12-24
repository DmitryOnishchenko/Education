package com.donishchenko.testgame.engine;

import com.donishchenko.testgame.Application;
import com.donishchenko.testgame.assets.Assets;
import com.donishchenko.testgame.gamestate.GameStateManager;
import com.donishchenko.testgame.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.util.ArrayList;

@Component("engineV3")
public class EngineV3 implements GameEngine {

    @Autowired @Qualifier("gameWindow")
    private GameWindow window;
    @Autowired
    private RenderThread renderThread;
    @Autowired
    private UpdateThread updateThread;

    @Autowired
    private GameStateManager gsm;

    // TODO test graphics
    private VolatileImage frame;
    private BufferedImage test_1;
//    private BufferedImage floor_1;

    private VolatileImage test_2;

    private ArrayList<BufferedImage> list;
    private ArrayList<VolatileImage> newList;

    @Override
    public void init() {
        test_1 = ImageUtils.toCompatibleImage(ImageUtils.loadImage(Application.class, "/background/map_ideal_0.png"));
        test_2 = ImageUtils.convertToVolatileImage(test_1);

//        floor_1 = ImageUtils.toCompatibleImage(ImageUtils.loadImage(Application.class, "/background/map_ideal_1.png"));

        list = (ArrayList<BufferedImage>) Assets.getProperties("effectsAssets").get("bloodSprites");

        newList = new ArrayList<>();
        for (BufferedImage img : list) {
            newList.add(ImageUtils.convertToVolatileImage(img));
        }

        frame = ImageUtils.createVolatileImage(window.getWidth(), window.getHeight(), Transparency.TRANSLUCENT);

        gsm.init();
    }

    @Override
    public void start() {
        window.setVisible(true);

        renderThread.start();
        updateThread.start();
    }

    @Override
    public void processInput() {
        gsm.processInput(null);
    }

    @Override
    public void update() {
        gsm.update();
    }

    @Override
    public void render() {
        Graphics2D g2 = (Graphics2D) frame.getGraphics();

        /* Clear */
        g2.clearRect(0, 0, window.getWidth(), window.getHeight());

        /* Render */
        // TEST
        /*optional
        -Dsun.java2d.opengl=true
        208 825 200 px ~27ms*/
//        for (int i = 0; i < 100; i++) {
//            g2.drawImage(test_2, 0, 0, null);
//        }

        // Real work
        gsm.render(g2);

        /* FPS/TPS info */
        g2.setColor(Color.WHITE);
        g2.drawString(renderThread.report(), 5, 18);

        g2.dispose();

        window.getContentPane().getGraphics().drawImage(frame, 0, 0, null);
        window.getContentPane().getGraphics().dispose();
    }

    @Override
    public void onError(String errorMessage) {
        JOptionPane.showMessageDialog(window, errorMessage);
        System.exit(0);
    }
}
