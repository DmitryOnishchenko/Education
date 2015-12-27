package com.donishchenko.testgame.gamestate.battle;

import com.donishchenko.testgame.config.GameConstants;

public class BattleStateSettings {

    public static volatile boolean PAUSE;
    public static volatile boolean DEMO_MODE = true;
    public static volatile boolean DEBUG_MODE;
    public static volatile boolean DEBUG_BOX;
    public static volatile boolean DEBUG_TARGET;
    public static volatile boolean DEBUG_GRID;

    public static int leftSpawnPoint = Grid.INDENT_LEFT;
    public static int rightSpawnPoint = GameConstants.DEFAULT_WIDTH + 50;

    /* Test */
    public static int testSpawnTimer;
    public static int testSpawnTimer2;
}
