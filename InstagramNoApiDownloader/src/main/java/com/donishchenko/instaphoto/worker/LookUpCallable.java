package com.donishchenko.instaphoto.worker;

import com.donishchenko.instaphoto.model.Target;

import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LookUpCallable implements Callable<Integer> {
    private String mainUrl;
    private Target target;

    public LookUpCallable(String mainUrl, Target target) {
        this.mainUrl = mainUrl;
        this.target = target;
    }

    @Override
    public Integer call() throws Exception {
        String targetUrl = mainUrl + "/" + target.getName();

        String data = InstaParser.getData(targetUrl);

        String mediaCount = "\"media\":\\{\"count\":([0-9]+)";
        Pattern pattern = Pattern.compile(mediaCount);
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        return 0;
    }
}
