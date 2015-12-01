package com.donishchenko.instaphoto.worker;

import com.donishchenko.instaphoto.controller.Config;
import com.donishchenko.instaphoto.controller.MainController;
import com.donishchenko.instaphoto.logger.ConsolePrinter;
import com.donishchenko.instaphoto.model.Target;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Downloader {
    private static final ConsolePrinter printer = ConsolePrinter.getInstance();

    private MainController mainController;
    private Config config;
    private ExecutorService executor;
    private ExecutorCompletionService<Integer> lookUpService;
    private ExecutorCompletionService<List<DownloadTask>> searchService;

    public Downloader(MainController mainController, Config config) {
        this.mainController = mainController;
        this.config = config;
    }

    public void init() {
        if (executor == null) {
            executor = Executors.newFixedThreadPool(config.getThreads());
            lookUpService = new ExecutorCompletionService<>(executor);
            searchService = new ExecutorCompletionService<>(executor);
        }
    }

    public void search() throws InterruptedException, ExecutionException {
        List<Target> targets = config.getTargets();

        /* Get total work */
        int totalWork = 0;
        for (Target target : targets) {
            lookUpService.submit(new LookUpCallable(config.getMainUrl(), target));
        }

        for (int i = 0; i < targets.size(); i++) {
            int work = lookUpService.take().get();
            totalWork += work;
        }
        mainController.setTotalWork(totalWork);

        /* Search */
        for (Target target : targets) {
            String targetUrl = config.getMainUrl() + "/" + target.getName();
            searchService.submit(new SearchCallable(this, target, targetUrl));
        }
    }

    public void download() throws InterruptedException, ExecutionException {
        prepareDirectories();

        /* Set progress bar */
        int totalWork = 0;
        for (Target target : config.getTargets()) {
            totalWork += target.getDownloadTasks().size();
        }
        mainController.setTotalWork(totalWork);

        /* Submit download tasks */
        ExecutorCompletionService<String> service = new ExecutorCompletionService<>(executor);
        for (Target target : config.getTargets()) {
            for (DownloadTask task : target.getDownloadTasks()) {
                service.submit(new DownloadCallable(task));
            }
        }

        for (int i = 0; i < totalWork; i++) {
            String message = service.take().get();
            if (!message.equals("Error")) {
                printer.time().print("<b style=\"color: #2e6409\">Success: </b>" + message).br();
            }
            mainController.addProgress(1);
        }
    }

    private void prepareDirectories() {
        for (Target target : config.getTargets()) {
            String dirString = target.getDirectory();

            if (dirString == null) {
                dirString = target.getName();
            }
            dirString = config.getDefaultDirectory() + "/" + dirString;
            target.setDirectory(dirString);

            File directory = new File(dirString);
            if (!directory.exists()) {
                directory.mkdirs();
            }
        }
    }

    public void submitNewSearchTask(Target target, String targetUrl) {
        searchService.submit(new SearchCallable(this, target, targetUrl));
    }

    public synchronized void addProgress(int value) {
        mainController.addProgress(value);
    }
}