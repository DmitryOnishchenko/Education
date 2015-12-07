package com.donishchenko.instaphoto.worker;

import com.donishchenko.instaphoto.controller.Config;
import com.donishchenko.instaphoto.logger.ConsolePrinter;
import com.donishchenko.instaphoto.model.Target;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.List;

public class FilesChecker {
    private static final ConsolePrinter printer = ConsolePrinter.getInstance();

    private Config config;

    public FilesChecker(Config config) {
        this.config = config;
    }

    public void checkFiles() throws NoSuchFileException {
        File defaultDirectory = new File(config.getDefaultDirectory());
        if (!defaultDirectory.exists()) {
            String message = "No such directory: " + defaultDirectory.getName();
            printer.printError(message);
            throw new NoSuchFileException(message);
        }

        for (Target target : config.getTargets()) {
            File targetDirectory = new File(target.getDirectory());
            File[] files = targetDirectory.listFiles();



            List<DownloadTask> tasks = target.getDownloadTasks();

//            for (DownloadTask task : tasks) {
//
//            }
        }
    }
}
