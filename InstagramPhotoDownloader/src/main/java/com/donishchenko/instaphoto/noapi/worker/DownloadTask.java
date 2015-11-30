package com.donishchenko.instaphoto.noapi.worker;

import com.donishchenko.instaphoto.noapi.model.Target;

public class DownloadTask {
    private String url;
    private Target target;

    public DownloadTask(String url, Target target) {
        this.url = url;
        this.target = target;
    }

    public String getUrl() {
        return url;
    }

    public Target getTarget() {
        return target;
    }
}
