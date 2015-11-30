package com.donishchenko.instaphoto.noapi.worker;

import com.donishchenko.instaphoto.noapi.controller.Config;
import com.donishchenko.instaphoto.noapi.controller.MainController;
import com.donishchenko.instaphoto.noapi.model.Target;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static com.donishchenko.instaphoto.noapi.logger.ConsoleLogger.log;

public class Downloader {
    private MainController mainController;
    private Config config;
    private ExecutorCompletionService<List<URL>> service;
    private List<URL> downloadList = new ArrayList<>();

    //TODO test total work
    private int totalWork;

    public Downloader(MainController mainController, Config config) {
        this.mainController = mainController;
        this.config = config;
    }

    public void init() {
        service = new ExecutorCompletionService<>(Executors.newFixedThreadPool(config.getThreads()));
    }

    public List<URL> getDownloadList() {
        return downloadList;
    }

    public void search() throws InterruptedException, ExecutionException {
        List<Target> targets = config.getTargets();

        for (Target target : targets) {
            service.submit(new SearchTask(this, config.getMainUrl(), target));
        }

        for (int i = 0; i < targets.size(); i++) {
            List<URL> list = service.take().get();
            downloadList.addAll(list);
        }

        log("Total: " + downloadList.size());
    }

    public synchronized void addWork(int work) {
        totalWork += work;
    }
}
