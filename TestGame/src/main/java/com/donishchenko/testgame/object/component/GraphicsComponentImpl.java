package com.donishchenko.testgame.object.component;

import com.donishchenko.testgame.object.GameObject;
import com.donishchenko.testgame.object.Side;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.donishchenko.testgame.gamestate.battle.BattleStateSettings.*;

public class GraphicsComponentImpl implements GraphicsComponent {

    private GameObject gameObject;

    // frame variables
    private int frameHalfWidth;
    private int frameHalfHeight;

    // debug
    private Color debugTargetColor;

    public GraphicsComponentImpl(GameObject gameObject) {
        this.gameObject = gameObject;
        this.debugTargetColor = (gameObject.side == Side.LeftArmy ? Color.YELLOW : Color.BLUE);
    }

    @Override
    public void update() {
        gameObject.action.updateAnimation();
    }

    @Override
    public void render(Graphics2D g2) {
        if (!gameObject.visible) return;

        if (DEBUG_MODE) {
            if (gameObject.isAlive()) {
                if (DEBUG_BOX) {
                    g2.setPaint(Color.WHITE);
                    g2.draw(gameObject.hitBox);
                    g2.draw(gameObject.searchCircle);
                    g2.setPaint(Color.GREEN);
                    g2.draw(gameObject.attackBox);
                }
                if (DEBUG_TARGET) {
                    GameObject target = gameObject.target;
                    if (target != null) {
                        g2.setPaint(debugTargetColor);
                        g2.drawLine(gameObject.x(), gameObject.y(), target.x(), target.y());
                    }
                }
            }
        }

        BufferedImage image = gameObject.action.getCurrentFrame();
        g2.drawImage(image, gameObject.x() - frameHalfWidth, gameObject.y() - frameHalfHeight, null);
    }
}
