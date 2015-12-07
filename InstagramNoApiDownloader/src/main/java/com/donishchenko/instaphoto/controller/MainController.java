package com.donishchenko.instaphoto.controller;

import com.donishchenko.instaphoto.gui.MainWindow;
import com.donishchenko.instaphoto.logger.ConsolePrinter;
import com.donishchenko.instaphoto.worker.Downloader;

import java.util.concurrent.ExecutionException;

public class MainController {
    private static final ConsolePrinter printer = ConsolePrinter.getInstance();

    private MainWindow mainWindow;
    private Config config = new Config();
    private Downloader downloader = new Downloader(this, config);
    private volatile boolean isWorking;

    public MainController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void init() {
        config.init();
        downloader.init();

        printer.time().print("Targets: <b>[ " + config.getTargetsNames() + " ]</b>").br();
    }

    public void search() {
        if (!isWorking) {
            isWorking = true;
        } else {
            return;
        }

        if (!config.checkConfig()) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    downloader.search();
                } catch (InterruptedException | ExecutionException e) {
                    isWorking = false;
                    printer.time().printError(e.getMessage()).br();
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void download() {
        if (!isWorking) {
            isWorking = true;
        } else {
            return;
        }

        if (!downloader.haveWork()) {
            printer.time().print("No work. First try to click <b>\"Search\"</b> button").br();
            isWorking = false;
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    downloader.download();
                } catch (InterruptedException | ExecutionException e) {
                    isWorking = false;
                    printer.time().printError(e.getMessage()).br();
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void setTotalWork(int totalWork) {
        mainWindow.setTotalWork(totalWork);
    }

    public void setProgress(int totalWork) {
        mainWindow.setProgress(totalWork);
    }

    public synchronized void addProgress(int value) {
        mainWindow.addProgress(value);
        int progress = mainWindow.getProgress();
        if (progress == mainWindow.getTotalWork()) {
            printer.time().print("<b>Task ended<b>").br();
            isWorking = false;
        }
    }
}
