package com.donishchenko.instaphoto.noapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Target {
    @JsonProperty
    private String name;

    @JsonProperty
    private String directory;

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
}
