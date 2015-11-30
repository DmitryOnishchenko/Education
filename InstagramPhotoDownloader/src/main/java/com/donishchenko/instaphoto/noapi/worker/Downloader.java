package com.donishchenko.instaphoto.noapi.worker;

import com.donishchenko.instaphoto.noapi.controller.Config;
import com.donishchenko.instaphoto.noapi.controller.MainController;
import com.donishchenko.instaphoto.noapi.model.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static com.donishchenko.instaphoto.noapi.logger.ConsoleLogger.log;

public class Downloader {
    private MainController mainController;
    private Config config;
    private ExecutorService executor;
    private List<DownloadTask> downloadList = new ArrayList<>();

    //TODO test total work
    private int totalWork;

    public Downloader(MainController mainController, Config config) {
        this.mainController = mainController;
        this.config = config;
    }

    public void init() {
        executor = Executors.newFixedThreadPool(config.getThreads());
    }

    public List<DownloadTask> getDownloadList() {
        return downloadList;
    }

    public void search() throws InterruptedException, ExecutionException {
        ExecutorCompletionService<List<DownloadTask>> service = new ExecutorCompletionService<>(executor);
        List<Target> targets = config.getTargets();

        for (Target target : targets) {
            service.submit(new SearchTask(this, config.getMainUrl(), target));
        }

        for (int i = 0; i < targets.size(); i++) {
            List<DownloadTask> list = service.take().get();
            downloadList.addAll(list);
        }

        log("Total: " + downloadList.size());
    }

    public void download() throws InterruptedException, ExecutionException {
        ExecutorCompletionService<String> service = new ExecutorCompletionService<>(executor);
        for (DownloadTask task : downloadList) {
            service.submit(new DownloadCallable(task));
        }

        for (int i = 0; i < downloadList.size(); i++) {
            String message = service.take().get();

            log(message);
        }
    }

    public synchronized void addWork(int work) {
        totalWork += work;
    }
}
