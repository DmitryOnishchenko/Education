package com.donishchenko.testgame.engine;

import com.donishchenko.testgame.Application;
import com.donishchenko.testgame.assets.Assets;
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

    // TODO test graphics
    private VolatileImage frame;
    private BufferedImage floor_0;
    private BufferedImage floor_1;
    private ArrayList<BufferedImage> list;
    private ArrayList<VolatileImage> newList;

    @Override
    public void init() {
        BufferedImage sprite = ImageUtils.loadImage(Application.class, "/background/map_ideal_0.png");
        floor_0 = ImageUtils.toCompatibleImage(sprite);
        floor_1 = ImageUtils.toCompatibleImage(ImageUtils.loadImage(Application.class, "/background/map_ideal_1.png"));

        list = (ArrayList<BufferedImage>) Assets.getProperties("effectsAssets").get("bloodSprites");

        newList = new ArrayList<>();
        for (BufferedImage img : list) {
            newList.add(ImageUtils.convertToVolatileImage(img));
        }

        frame = ImageUtils.createVolatileImage(window.getWidth(), window.getHeight(), Transparency.TRANSLUCENT);
    }

    @Override
    public void start() {
        window.setVisible(true);

        renderThread.start();
        updateThread.start();
    }

    @Override
    public void processInput() {

    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        Graphics2D g2 = frame.createGraphics();

        /* Clear */
//        g2.fillRect(0, 0, window.getWidth(), window.getHeight());

//        -Dsun.java2d.opengl=true
        /* Render */
        g2.drawImage(floor_0, 0, 0, null);

        for (int row = 0; row < 100; row++) {
            for (int col = 0; col < 200; col++) {
                BufferedImage img = list.get(0);
                g2.drawImage(img, col * 20, row * 20, null);
            }
        }

        g2.drawImage(floor_1, 0, 0, null);
        //

        /* FPS/TPS info */
        g2.setColor(Color.WHITE);
        g2.drawString(renderThread.report(), 5, 18);

        g2.dispose();

        window.getContentPane().getGraphics().drawImage(frame, 0, 0, null);
    }

    @Override
    public void onError(String errorMessage) {
        JOptionPane.showMessageDialog(window, errorMessage);
        System.exit(0);
    }
}
