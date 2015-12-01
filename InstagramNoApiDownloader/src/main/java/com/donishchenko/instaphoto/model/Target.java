package com.donishchenko.instaphoto.model;

import com.donishchenko.instaphoto.worker.DownloadTask;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Target {
    @JsonProperty
    private String name;

    @JsonProperty
    private String directory;

    @JsonIgnore
    private List<DownloadTask> downloadTasks = new ArrayList<>();

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

    public List<DownloadTask> getDownloadTasks() {
        return downloadTasks;
    }

    public synchronized void addTasks(List<DownloadTask> tasks) {
        downloadTasks.addAll(tasks);
    }
}
