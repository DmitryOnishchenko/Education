package com.donishchenko.testgame.assets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.awt.image.VolatileImage;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GraphicsModel {

    private int widthSprite;
    private int heightSprite;
    private int baseLine;
    private int animationSpeed;

    private VolatileImage[] moveSpritesRight;
    private VolatileImage[] moveSpritesLeft;

    private VolatileImage[] fightSpritesRight;
    private VolatileImage[] fightSpritesLeft;

    private VolatileImage[] dieSpritesRight;
    private VolatileImage[] dieSpritesLeft;

}
