package com.donishchenko.testgame.assets;

import com.donishchenko.testgame.object.Race;
import com.donishchenko.testgame.object.Side;
import com.donishchenko.testgame.object.Vector2F;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PhysicsModel {

    private String name;
    private Side side;
    private Race race;

    private int maxHp;
    private int armor;
    private int damage;
    private float defaultSpeed;
    private float attackSpeed;
    private float attackRange;
    private float searchRange;

    private int spawnPrice;
    private int pricePerHead;

    private Vector2F moveDir;
    private float hitBoxWidth;
    private float hitBoxHeight;

}
