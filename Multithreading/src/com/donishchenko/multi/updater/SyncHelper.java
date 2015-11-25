package com.donishchenko.multi.updater;

public class SyncHelper {
    /* Sync objects */
    public static final Object ai = new Object();
    public static final Object action = new Object();
    public static final Object physics = new Object();
    public static final Object graphics = new Object();
    public static final Object gameLoop = new Object();

    /* Updates to process */
    public static final int PACKAGE = 20;

    /* Processed processed */
    public static volatile int AI_PROCESSED;
    public static volatile int ACTION_PROCESSED;
    public static volatile int PHYSICS_PROCESSED;
    public static volatile int GRAPHICS_PROCESSED;

    public static volatile boolean gameLoopDone = false;
}
