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
        setMinimumSize(new Dimension(500, 200));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /* Console panel */
        JPanel consolePanel = new JPanel(new BorderLayout());

        final JTextPane textPane = new JTextPane();

        textPane.setEditable(false);
        textPane.setBackground(Color.decode(StyleProps.CONSOLE_BACKGROUND_COLOR));
        textPane.setContentType("text/html");

        /* Init ConsolePrinter doc */
        ConsolePrinter.setDoc(textPane.getStyledDocument());

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight()));

        consolePanel.add(scrollPane, BorderLayout.CENTER);
        add(consolePanel, BorderLayout.CENTER);

        /* Button panel */
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.decode(StyleProps.MAIN_BACKGROUND_COLOR));
        /* Buttons preferred size */
        Dimension buttonsDimension = new Dimension(100, 30);

        /* Init button */
        JButton initButton = new JButton("Init");
        initButton.setPreferredSize(buttonsDimension);
        initButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetProgress();
                mainController.init();
            }
        });
        buttonPanel.add(initButton);

        /* Clear button */
        JButton clearButton = new JButton("Clear");
        clearButton.setPreferredSize(buttonsDimension);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetProgress();
                textPane.setText("");
            }
        });
        buttonPanel.add(clearButton);

        /* Search button */
        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(buttonsDimension);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetProgress();
                mainController.search();
            }
        });
        buttonPanel.add(searchButton);

        /* Download button */
        JButton downloadButton = new JButton("Download");
        downloadButton.setPreferredSize(buttonsDimension);
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetProgress();
                mainController.download();
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
