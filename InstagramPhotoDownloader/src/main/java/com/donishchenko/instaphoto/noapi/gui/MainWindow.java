package com.donishchenko.instaphoto.noapi.gui;

import com.donishchenko.instaphoto.noapi.controller.MainController;
import com.donishchenko.instaphoto.noapi.logger.ConsoleLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private static final String TITLE = "Instagram NoAPI";
    private static final int DEFAULT_WIDTH   = 1000;
    private static final int DEFAULT_HEIGHT  = 600;

    private MainController mainController = new MainController();

    public MainWindow() {
        setTitle(TITLE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);

        /* Log panel */
        JPanel logPanel = new JPanel(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        ConsoleLogger.setTextArea(textArea);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight()));
        logPanel.add(scrollPane, BorderLayout.CENTER);

        add(logPanel, BorderLayout.CENTER);

        /* Button panel */
        JPanel buttonPanel = new JPanel();
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.startWork();
            }
        });

        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.NORTH);
    }
}
