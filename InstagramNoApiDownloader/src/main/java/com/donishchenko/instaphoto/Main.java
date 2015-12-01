package com.donishchenko.instaphoto;

import com.donishchenko.instaphoto.gui.MainWindow;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow window = new MainWindow();
                window.init();
                window.setVisible(true);
            }
        });
    }
}
