package com.donishchenko.testgame.engine;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

import static com.donishchenko.testgame.config.GameConstants.*;

@Component("gameWindow")
public class GameWindow extends JFrame {

    public GameWindow() {
        setTitle(TITLE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setContentPane(new JPanel());
        getContentPane().setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        getContentPane().setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

}
