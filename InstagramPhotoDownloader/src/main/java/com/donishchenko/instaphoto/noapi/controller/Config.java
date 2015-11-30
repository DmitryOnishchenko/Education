package com.donishchenko.instaphoto.noapi.controller;

import com.donishchenko.instaphoto.noapi.logger.LogLevel;
import com.donishchenko.instaphoto.noapi.model.Target;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Date;
import java.util.List;

import static com.donishchenko.instaphoto.noapi.logger.ConsoleLogger.log;
import static com.donishchenko.instaphoto.noapi.logger.ConsoleLogger.logError;

public class Config {
    private File configFile = new File("config.json");
    private long lastModified;

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

    public boolean checkConfig() {
        boolean valid = true;

        /* Check config file */
        if (!configFile.exists()) {
            logError("Check config.json! It doesn't exist.");
            return false;
        }

        try {
            /* Check for modification. Update config if needed */
            long checkModified = new Date(configFile.lastModified()).getTime();
            if (checkModified > lastModified) {
                lastModified = checkModified;
                updateConfig();
            }
        } catch (IOException e) {
            valid = false;
            log(e.getMessage());
        }

        return valid;
    }

    private void updateConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.readerForUpdating(this).readValue(configFile);

        updateTargets();
        checkDirectories();
    }

    private void updateTargets() throws IOException {
        File targetsFile = new File(targetsFileName);
        if (!targetsFile.exists()) {
            logError("No such file: " + targetsFileName);
            throw new NoSuchFileException(targetsFileName);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.readerForUpdating(this).readValue(targetsFile);
    }

    private void checkDirectories() {
        for (Target target : targets) {
            String dirString = target.getDirectory();

            if (dirString == null) {
                dirString = target.getName();
            }
            dirString = defaultDirectory + "/" + dirString;
            target.setDirectory(dirString);

            File directory = new File(dirString);
            if (!directory.exists()) {
                directory.mkdirs();
            }
        }
    }

    public File getConfigFile() {
        return configFile;
    }

    public long getLastModified() {
        return lastModified;
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
