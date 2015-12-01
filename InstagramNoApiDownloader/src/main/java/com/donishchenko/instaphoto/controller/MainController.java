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
    private volatile boolean working;

    public MainController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void init() {
        config.init();
        downloader.init();

        printer.time().print("Targets: <b>[ " + config.getTargetsNames() + " ]</b>").br();
    }

    public void search() {
        if (!working) {
            working = true;
        } else {
            return;
        }
        if (!config.checkConfig()) {
            return;
        }

        try {
            printer.time().print("<b>Search started</b>").br();
            downloader.search();
        } catch (InterruptedException | ExecutionException e) {
            printer.time().printError(e.getMessage()).br();
            e.printStackTrace();
        }
    }

    public void download() {
        try {
            if (!working) {
                working = true;
                printer.time().print("<b>Download started</b>").br();

                downloader.download();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
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
            working = false;
//            List<Target> targets = config.getTargets();
//            for (Target target : targets) {
//                printer.time().print(
//                        "<span style=\"color: #493319\"><b>" + target.getName() + "</b></span> : found " +
//                                target.getDownloadTasks().size() + " images.");
//            }
        }
    }
//
//    public void resetProgress() {
//        mainWindow.resetProgress();
//    }
}
