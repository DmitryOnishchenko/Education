package com.donishchenko.instaphoto.gui;

import com.donishchenko.instaphoto.controller.MainController;
import com.donishchenko.instaphoto.logger.ConsolePrinter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private static final String TITLE = "Instagram NoAPI";
    private static final int DEFAULT_WIDTH   = 1100;
    private static final int DEFAULT_HEIGHT  = 600;

    private JProgressBar progressBar;
    private MainController mainController = new MainController(this);

    public MainWindow() {
        setTitle(TITLE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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

        ConsolePrinter.setDoc(textPane.getStyledDocument());

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight()));

        logPanel.add(scrollPane, BorderLayout.CENTER);
        add(logPanel, BorderLayout.CENTER);

        /* Button panel */
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.decode(StyleProps.MAIN_BACKGROUND_COLOR));

        JButton initButton = new JButton("Init");
        initButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        resetProgress();
                        mainController.init();
                    }
                }).start();
            }
        });
        buttonPanel.add(initButton);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        resetProgress();
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
                resetProgress();
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
                        resetProgress();
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
        progressBar.setBackground(Color.decode(StyleProps.MAIN_BACKGROUND_COLOR));
        progressBar.setForeground(Color.decode("#d4a752"));
        add(progressBar, BorderLayout.SOUTH);

        pack();
    }

    public void init() {
        mainController.init();
    }

    public int getTotalWork() {
        return progressBar.getMaximum();
    }

    public void setTotalWork(int value) {
        progressBar.setMaximum(value);
    }

    public int getProgress() {
        return progressBar.getValue();
    }

    public void setProgress(int totalWork) {
        progressBar.setValue(totalWork);
    }

    public void addProgress(int value) {
        int total = progressBar.getValue() + value;
        progressBar.setValue(total);
        progressBar.setString(total + "/" + progressBar.getMaximum());
    }

    public void resetProgress() {
        progressBar.setValue(0);
        progressBar.setMaximum(0);
        progressBar.setString("0%");
    }
}
