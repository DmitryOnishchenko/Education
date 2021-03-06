package com.donishchenko.instaphoto.worker;

import com.donishchenko.instaphoto.controller.Config;
import com.donishchenko.instaphoto.controller.MainController;
import com.donishchenko.instaphoto.logger.ConsolePrinter;
import com.donishchenko.instaphoto.model.Target;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Worker {
    private static final ConsolePrinter printer = ConsolePrinter.getInstance();

    private MainController mainController;
    private Config config;
    private FilesChecker filesChecker;

    private ExecutorService executor;
    private ExecutorCompletionService<Integer> lookUpService;
    private ExecutorCompletionService<Map<String, DownloadTask>> searchService;

    public Worker(MainController mainController, Config config) {
        this.mainController = mainController;
        this.config = config;
        this.filesChecker = new FilesChecker(config);
    }

    public void init() {
        if (config.getConfigFile().exists() && executor == null) {
            executor = Executors.newFixedThreadPool(config.getThreads());
            lookUpService = new ExecutorCompletionService<>(executor);
            searchService = new ExecutorCompletionService<>(executor);
        }
    }

    public void search() throws InterruptedException, ExecutionException {
        printer.time().print("<b>Search started</b>").br();

        List<Target> targets = config.getTargets();

        /* Get total work */
        int totalWork = 0;
        for (Target target : targets) {
            printer.time()
                    .print("Try to connect to: <span style=\"color: #493319\"><b>" + target.getName() + "</b></span>").br();

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

    public void checkFiles() throws NoSuchFileException {
        filesChecker.checkFiles();
    }

    public void download() throws InterruptedException, ExecutionException, NoSuchFileException {
        printer.time().print("<b>Download started</b>").br();

        /* Set progress bar */
        int totalWork = 0;
        for (Target target : config.getTargets()) {
            totalWork += target.getDownloadTasks().size();
        }
        mainController.setTotalWork(totalWork);

        /* Submit download tasks */
        ExecutorCompletionService<String> service = new ExecutorCompletionService<>(executor);
        for (Target target : config.getTargets()) {
            for (DownloadTask task : target.getDownloadTasks().values()) {
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

    public boolean haveWork() {
        if (config.getTargets() == null) return false;

        for (Target target : config.getTargets()) {
            if (!target.getDownloadTasks().isEmpty()) {
                return true;
            }
        }

        return false;
    }

    public void prepareDirectories() {
        for (Target target : config.getTargets()) {
            String dirString = target.getDirectory();

            if (dirString == null) {
                dirString = target.getName();
                target.setDirectory(dirString);
            }
            dirString = config.getDefaultDirectory() + "/" + dirString;

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
