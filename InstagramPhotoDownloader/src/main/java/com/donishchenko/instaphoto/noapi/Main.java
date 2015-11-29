package com.donishchenko.instaphoto.noapi;

import com.donishchenko.instaphoto.noapi.gui.MainWindow;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow window = new MainWindow();
                window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                window.pack();
                window.setVisible(true);
            }
        });
    }
}
