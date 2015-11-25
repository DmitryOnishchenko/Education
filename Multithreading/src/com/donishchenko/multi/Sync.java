package com.donishchenko.multi;

import javax.swing.*;

public class Sync {
    private static Object ai = new Object();
    private static Object action = new Object();
    private static Object physics = new Object();
    private static Object graphics = new Object();
    private static Object gameLoop = new Object();
    public static final int UNITS = 20;
    public static final int SLEEP = 20;

    public static volatile int aiProcessed;

    public static Object getAi() {
        return ai;
    }

    public static Object getAction() {
        return action;
    }

    public static Object getPhysics() {
        return physics;
    }

    public static Object getGraphics() {
        return graphics;
    }

    public static Object getGameLoop() {
        return gameLoop;
    }

    public static void waitAi() throws InterruptedException {
        ai.wait();
    }

    public static void notifyAi() {
        ai.notify();
    }

    public static void waitAction() throws InterruptedException {
        action.wait();
    }

    public static void notifyAction() {
        action.notify();
    }

    public static void waitPhysics() throws InterruptedException {
        physics.wait();
    }

    public static void notifyPhysics() {
        physics.notify();
    }

    public static void waitGraphics() throws InterruptedException {
        graphics.wait();
    }

    public static void notifyGraphics() {
        graphics.notify();
    }

    public static void waitGameLoop() throws InterruptedException {
        gameLoop.wait();
    }

    public static void notifyGameLoop() {
        gameLoop.notify();
    }

    public static void showMessage(Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}
