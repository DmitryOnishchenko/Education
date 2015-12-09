package com.donishchenko.instaphoto.controller;

import com.donishchenko.instaphoto.gui.MainWindow;
import com.donishchenko.instaphoto.logger.ConsolePrinter;
import com.donishchenko.instaphoto.worker.Worker;

import java.nio.file.NoSuchFileException;
import java.util.concurrent.ExecutionException;

public class MainController {
    private static final ConsolePrinter printer = ConsolePrinter.getInstance();

    private MainWindow mainWindow;
    private Config config = new Config();
    private Worker worker = new Worker(this, config);
    private volatile boolean isWorking;

    public MainController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void init() {
        config.init();
        worker.init();

        printer.time().print("Targets: <b>[ " + config.getTargetsNames() + " ]</b>").br();
    }

    public void search() {
        if (!isWorking) {
            isWorking = true;
        } else {
            return;
        }

        if (!config.checkConfig()) {
            isWorking = false;
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    worker.search();
                } catch (InterruptedException | ExecutionException e) {
                    printer.time().printError(e.getMessage()).br();
                    e.printStackTrace();
                } finally {
                    isWorking = false;
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

        if (!config.getConfigFile().exists()) {
            printer.time().printError("Check \"config.json\"! It doesn't exist.").br();
            isWorking = false;
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    worker.prepareDirectories();
                    worker.checkFiles();

                    if (!worker.haveWork()) {
                        printer.time().print(
                                "No work. <span style=\"color: #2e6409\"><b>All is up-to-date</b></span>. Click <b>\"Search\"</b> button for new task").br();
                    } else {
                        worker.download();
                    }
                } catch (InterruptedException | ExecutionException | NoSuchFileException e) {
                    printer.time().printError(e.getMessage()).br();
                    e.printStackTrace();
                } finally {
                    isWorking = false;
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
            printer.time().print("<b style=\"color: #2e6409\">Task ended<b>").br();
            isWorking = false;
        }
    }
}
