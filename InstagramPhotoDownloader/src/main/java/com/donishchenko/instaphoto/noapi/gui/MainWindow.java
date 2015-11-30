package com.donishchenko.instaphoto.noapi.gui;

import com.donishchenko.instaphoto.noapi.controller.MainController;
import com.donishchenko.instaphoto.noapi.logger.ConsoleLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.donishchenko.instaphoto.noapi.logger.ConsoleLogger.log;

public class MainWindow extends JFrame {
    private static final String TITLE = "Instagram NoAPI";
    private static final int DEFAULT_WIDTH   = 1000;
    private static final int DEFAULT_HEIGHT  = 600;

    private JProgressBar progressBar;
    private MainController mainController = new MainController(this);

    public MainWindow() {
        setTitle(TITLE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);

        /* Log panel */
        JPanel logPanel = new JPanel(new BorderLayout());

        final JTextPane textPane = new JTextPane() {
            //TODO test RenderingHints
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D graphics2d = (Graphics2D) g;
                graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                super.paintComponent(g);
            }
        };

        textPane.setEditable(false);
        textPane.setBackground(Color.decode(StyleProps.CONSOLE_BACKGROUND_COLOR));
        textPane.setContentType("text/html");

        ConsoleLogger.setDoc(textPane.getStyledDocument());

        //TODO test message
        log("Test message");

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight()));

        logPanel.add(scrollPane, BorderLayout.CENTER);
        add(logPanel, BorderLayout.CENTER);

        /* Button panel */
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.decode(StyleProps.MAIN_BACKGROUND_COLOR));

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mainController.search();
                    }
                }).start();
            }
        });
        buttonPanel.add(searchButton);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textPane.setText("");
            }
        });
        buttonPanel.add(clearButton);

        JButton downloadButton = new JButton("Download");
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mainController.download();
                    }
                }).start();
            }
        });
        buttonPanel.add(downloadButton);

        /* Add button panel */
        add(buttonPanel, BorderLayout.NORTH);

        /* Progress bar */
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(getWidth(), 30));
        add(progressBar, BorderLayout.SOUTH);
    }

    public void setTotalWork(int value) {
        progressBar.setMaximum(value);
    }

    public void setProgress(int value) {
        progressBar.setValue(value);
    }
}
