package com.donishchenko.instaphoto.noapi.controller;

import com.donishchenko.instaphoto.noapi.gui.MainWindow;
import com.donishchenko.instaphoto.noapi.worker.Downloader;

import java.util.concurrent.ExecutionException;

import static com.donishchenko.instaphoto.noapi.logger.ConsoleLogger.logError;

public class MainController {
    private final MainWindow mainWindow;
    private Config config = new Config();
    private Downloader downloader = new Downloader(this, config);

    public MainController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void search() {
        if (!config.checkConfig()) {
            logError("Config not prepared.");
            return;
        }

        downloader.init();

        try {
            downloader.search();
        } catch (InterruptedException | ExecutionException e) {
            logError(e.getMessage());
            e.printStackTrace();
        }
    }

    public void download() {
        try {
            downloader.download();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void setTotalWork(int totalWork) {
        mainWindow.setTotalWork(totalWork);
    }
}
