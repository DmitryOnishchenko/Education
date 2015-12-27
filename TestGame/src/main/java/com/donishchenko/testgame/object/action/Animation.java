package com.donishchenko.testgame.object.action;

import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

public class Animation {
    public Action action;
    public int currentSprite = 0;
    private int animationTimer;
    public BufferedImage[] sprites;
    private BufferedImage[] spritesRight;
    private BufferedImage[] spritesLeft;
    public int previousDirX;

    public Animation(Action action, BufferedImage[] sprites,
                     BufferedImage[] spritesRight, BufferedImage[] spritesLeft) {
        this.action = action;
        this.sprites = sprites;
        this.spritesRight = spritesRight;
        this.spritesLeft = spritesLeft;
        this.previousDirX = 1;
        this.currentSprite = 0;
    }

    public void next() {
        if (++animationTimer >= action.object.gModel.getAnimationSpeed()) {
            currentSprite++;
            if (currentSprite == sprites.length) {
                currentSprite = 0;
                if (action.needFullAnimation) {
                    action.animationFinished();
                }
            }
            animationTimer = 0;
        }
    }

    public BufferedImage get() {
        try {
            if (currentSprite == sprites.length) {
                currentSprite = sprites.length - 1;
            }
            return sprites[currentSprite];
        } catch (ArrayIndexOutOfBoundsException e) {
            // TODO Animation: fix ArrayIndexOutOfBoundsException
            System.out.println("Return 0 index");
            return sprites[0];
        }
    }

    public void swapDir(float unitDirX) {
        previousDirX = unitDirX > 0 ? 1 : -1;
        if (sprites == spritesRight) {
            sprites = spritesLeft;
        } else {
            sprites = spritesRight;
        }
    }

    public boolean watchWest() {
        return previousDirX < 0;
    }

    public boolean watchEast() {
        return previousDirX > 0;
    }
}
