package com.donishchenko.testgame.object.action;

import javafx.animation.Animation;

import java.awt.image.BufferedImage;

public abstract class Action {

    private Animation animation;

    public void updateAnimation() {

    }

    public BufferedImage getCurrentFrame() {
        return null;
    }
}
