package com.donishchenko.testgame.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.awt.image.BufferedImage;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GraphicsModel {

    @JsonProperty private int widthSprite;
    @JsonProperty private int heightSprite;
    @JsonProperty private int baseLine;
    @JsonProperty private int animationSpeed;

    @JsonProperty private int[] moveSpritesIndex;
    @JsonProperty private BufferedImage[] moveSpritesRight;
    @JsonProperty private BufferedImage[] moveSpritesLeft;

    @JsonProperty private int[] fightSpritesIndex;
    @JsonProperty private BufferedImage[] fightSpritesRight;
    @JsonProperty private BufferedImage[] fightSpritesLeft;

    @JsonProperty private int[] dieSpritesIndex;
    @JsonProperty private BufferedImage[] dieSpritesRight;
    @JsonProperty private BufferedImage[] dieSpritesLeft;

    @JsonSetter("moveSpritesFile")
    public void setMoveSprites(String file) {
        moveSpritesRight = ImageUtils.loadImageTiles(file, widthSprite, heightSprite, ResourceLoader.SCALE);
        moveSpritesLeft = ImageUtils.flipHorizontally(moveSpritesRight);
    }

    @JsonSetter("fightSpritesFile")
    public void setFightSprites(String file) {
        fightSpritesRight = ImageUtils.loadImageTiles(file, widthSprite, heightSprite, ResourceLoader.SCALE);
        fightSpritesLeft = ImageUtils.flipHorizontally(fightSpritesRight);
    }

    @JsonSetter("dieSpritesFile")
    public void setDieSprites(String file) {
        dieSpritesRight = ImageUtils.loadImageTiles(file, widthSprite, heightSprite, ResourceLoader.SCALE);
        dieSpritesLeft = ImageUtils.flipHorizontally(dieSpritesRight);
    }

    public int getWidthSprite() {
        return widthSprite;
    }

    public void setWidthSprite(int widthSprite) {
        this.widthSprite = widthSprite;
    }

    public int getHeightSprite() {
        return heightSprite;
    }

    public void setHeightSprite(int heightSprite) {
        this.heightSprite = heightSprite;
    }

    public int getBaseLine() {
        return baseLine;
    }

    public void setBaseLine(int baseLine) {
        this.baseLine = baseLine;
    }

    public int getAnimationSpeed() {
        return animationSpeed;
    }

    public void setAnimationSpeed(int animationSpeed) {
        this.animationSpeed = animationSpeed;
    }

    public BufferedImage[] getMoveSpritesRight() {
        return moveSpritesRight;
    }

    public void setMoveSpritesRight(BufferedImage[] moveSpritesRight) {
        this.moveSpritesRight = moveSpritesRight;
    }

    public BufferedImage[] getMoveSpritesLeft() {
        return moveSpritesLeft;
    }

    public void setMoveSpritesLeft(BufferedImage[] moveSpritesLeft) {
        this.moveSpritesLeft = moveSpritesLeft;
    }

    public BufferedImage[] getFightSpritesRight() {
        return fightSpritesRight;
    }

    public void setFightSpritesRight(BufferedImage[] fightSpritesRight) {
        this.fightSpritesRight = fightSpritesRight;
    }

    public BufferedImage[] getFightSpritesLeft() {
        return fightSpritesLeft;
    }

    public void setFightSpritesLeft(BufferedImage[] fightSpritesLeft) {
        this.fightSpritesLeft = fightSpritesLeft;
    }

    public BufferedImage[] getDieSpritesRight() {
        return dieSpritesRight;
    }

    public void setDieSpritesRight(BufferedImage[] dieSpritesRight) {
        this.dieSpritesRight = dieSpritesRight;
    }

    public BufferedImage[] getDieSpritesLeft() {
        return dieSpritesLeft;
    }

    public void setDieSpritesLeft(BufferedImage[] dieSpritesLeft) {
        this.dieSpritesLeft = dieSpritesLeft;
    }
}
