package com.donishchenko.instaphoto.controller;

import com.donishchenko.instaphoto.logger.ConsolePrinter;
import com.donishchenko.instaphoto.logger.LogLevel;
import com.donishchenko.instaphoto.model.Target;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Config {
    private static final ConsolePrinter printer = ConsolePrinter.getInstance();

    private File configFile = new File("config.json");
    private File targetsFile;
    private long lastModifiedConfig;
    private long lastModifiedTargets;

    /* Config */
    @JsonProperty("main_url")
    private String mainUrl;

    @JsonProperty("default_directory")
    private String defaultDirectory;

    @JsonProperty("threads")
    private int nThreads;

    @JsonProperty("log_level")
    private LogLevel logLevel;

    /* Targets */
    @JsonProperty("targets_file_name")
    private String targetsFileName;

    @JsonProperty("targets")
    private List<Target> targets;

    public void init() {
        checkConfig();
    }

    public boolean checkConfig() {
        try {
            updateConfigFile();
            updateTargetsFile();

            for (Target target : targets) {
                target.getDownloadTasks().clear();
            }
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public String getTargetsNames() {
        if (targets == null || targets.isEmpty()) {
            return "empty";
        }

        StringBuilder builder = new StringBuilder();
        int i = 0;
        for ( ; i < targets.size() - 1; i++) {
            builder.append(targets.get(i).getName()).append(", ");
        }
        builder.append(targets.get(i).getName());

        return builder.toString();
    }

    private void updateConfigFile() throws IOException {
        /* Check config file */
        if (!configFile.exists()) {
            printer.printError("Check \"config.json\"! It doesn't exist.");
            throw new IOException();
        }

        /* Check for modification. Update config if needed */
        long checkModified = new Date(configFile.lastModified()).getTime();
        if (!(checkModified > lastModifiedConfig)) {
            return;
        }
        lastModifiedConfig = checkModified;

        ObjectMapper mapper = new ObjectMapper();
        mapper.readerForUpdating(this).readValue(configFile);
    }

    private void updateTargetsFile() throws IOException {
        if (targetsFile == null || !targetsFile.getName().equals(targetsFileName)) {
            targetsFile = new File(targetsFileName);
            lastModifiedTargets = 0;
        }

        /* Check targets file */
        if (!targetsFile.exists()) {
            printer.printError("No such file: " + targetsFileName);
            throw new IOException(targetsFileName);
        }

        /* Check for modification. Update targets if needed */
        long checkModified = new Date(targetsFile.lastModified()).getTime();
        if (!(checkModified > lastModifiedTargets)) {
            return;
        }
        lastModifiedTargets = checkModified;

        ObjectMapper mapper = new ObjectMapper();
        mapper.readerForUpdating(this).readValue(targetsFile);
    }

    public File getConfigFile() {
        return configFile;
    }

    public long getLastModifiedConfig() {
        return lastModifiedConfig;
    }

    public String getMainUrl() {
        return mainUrl;
    }

    public String getDefaultDirectory() {
        return defaultDirectory;
    }

    public int getThreads() {
        return nThreads;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public String getTargetsFileName() {
        return targetsFileName;
    }

    public List<Target> getTargets() {
        return targets;
    }
}
