package com.donishchenko.instaphoto.model;

import com.donishchenko.instaphoto.worker.DownloadTask;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class Target {
    @JsonProperty
    private String name;

    @JsonProperty
    private String directory;

    @JsonIgnore
    private String defaultDirectory;

    @JsonIgnore
    private Map<String, DownloadTask> downloadTasks = new HashMap<>();

    public Target() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getDefaultDirectory() {
        return defaultDirectory;
    }

    public void setDefaultDirectory(String defaultDirectory) {
        this.defaultDirectory = defaultDirectory;
    }

    public Map<String, DownloadTask> getDownloadTasks() {
        return downloadTasks;
    }

    public synchronized void addTasks(Map<String, DownloadTask> tasks) {
        downloadTasks.putAll(tasks);
    }
}
