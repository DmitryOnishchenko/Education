package com.donishchenko.testgame.object;

import com.donishchenko.testgame.gamestate.battle.Cell;
import com.donishchenko.testgame.object.action.Action;
import com.donishchenko.testgame.object.component.GraphicsComponent;
import com.donishchenko.testgame.object.component.GraphicsComponentImpl;
import com.donishchenko.testgame.resources.GraphicsModel;
import com.donishchenko.testgame.resources.PhysicsModel;
import com.donishchenko.testgame.resources.ResourceLoader;
import com.donishchenko.testgame.resources.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;

public class GameObject implements Comparable<GameObject> {

    private static int ID = 1;

    /* Common fields */
    public final int id = ID++;
    public final String name;
    public Side side;
    public Type type;
    public Vector2F pos;
    public Vector2F dir;
    public volatile boolean delete = false;

    /* Target */
    public volatile GameObject target;
    public int currHp;
    public int damage;
    public int armor;
    public float currentSpeed;

    /* Box model */
    public Ellipse2D.Double hitBox;
    public Ellipse2D.Double attackBox;
    public Ellipse2D.Double searchCircle;

    /* Models */
    public PhysicsModel physicsModel;
    public GraphicsModel graphicsModel;

    /* Components */
    public Cell cell;
    public boolean relocate;
//    public InputComponent inputComponent;
//    public AiContainer aiContainer;
    public Action action;
//    public PhysicsComponent physicsComponent;
    public GraphicsComponent graphicsComponent;

    /* Render variables */
    public boolean visible = true;
    public volatile int zLevel;
    public float yLevel;

    public GameObject(String name, Side side, float x, float y) {
        this.name = name;
        this.side = side;
        this.pos = new Vector2F(x, y);
    }

    public void init() {
        Resources resources = ResourceLoader.getResources(name);
        physicsModel = resources.get("physicsModel");
        graphicsModel = resources.get("graphicsModel");

        type    = physicsModel.getType();
        dir     = physicsModel.getMoveDir();
        zLevel  = physicsModel.getZLevel();
        yLevel  = pos.y;

        GraphicsComponent graphicsComponent = new GraphicsComponentImpl(this);
    }

    public void updateInput(KeyEvent event) {
//        if (inputComponent != null) inputComponent.update();
    }

    public void updateAi() {
//        if (aiContainer != null) aiContainer.update();
    }

    public void updateAction() {
//        if (action != null) action.execute();
    }

    public void updatePhysics() {
//        if (physicsComponent != null) physicsComponent.update();
    }

    public void updateGraphics() {
        if (graphicsComponent != null) graphicsComponent.update();
    }

    public void render(Graphics2D g2) {
        if (graphicsComponent != null) graphicsComponent.render(g2);
    }

    public int x() {
        return (int) pos.x;
    }

    public int y() {
        return (int) pos.y;
    }

    public boolean isAlive() {
        return currHp > 0;
    }

    /**
     * Compare method for rendering
     * @param obj
     * @return
     */
    @Override
    public int compareTo(GameObject obj) {
        int result = zLevel - obj.zLevel;
        if (result == 0) {
            result = Float.compare(yLevel, obj.yLevel);
        }
        return result;
    }

    public void switchState() {}

}
