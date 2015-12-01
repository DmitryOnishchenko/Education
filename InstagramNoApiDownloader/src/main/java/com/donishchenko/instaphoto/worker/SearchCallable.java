package com.donishchenko.instaphoto.worker;


import com.donishchenko.instaphoto.logger.ConsolePrinter;
import com.donishchenko.instaphoto.model.Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchCallable implements Callable<List<DownloadTask>> {
    private static final ConsolePrinter printer = ConsolePrinter.getInstance();

    private Downloader downloader;
    private Target target;
    private String targetUrl;

    public SearchCallable(Downloader downloader, Target target, String targetUrl) {
        this.downloader = downloader;
        this.target = target;
        this.targetUrl = targetUrl;
    }

    @Override
    public List<DownloadTask> call() throws Exception {
//        String targetUrl = mainUrl + "/" + target.getName();

        printer.time()
                .print("Try to connect to: <span style=\"color: #493319\"><b>" + target.getName() + "</b></span>").br();

        long start = System.currentTimeMillis();

        List<DownloadTask> list = process(targetUrl);

        long elapsed = System.currentTimeMillis() - start;

//        printer.time().print(
//                "<span style=\"color: #493319\"><b>" + target.getName() + "</b></span> : found " +
//                list.size() + " images in " + elapsed / 1000d + " sec.");

        return list;
    }

    private List<DownloadTask> process(String url) throws IOException {
        String data = InstaParser.getData(url);

        String maxId = checkForNextPage(data);
        if (maxId != null) {
            String param = "/?max_id=";
            StringBuilder builder = new StringBuilder();

            if (targetUrl.contains(param)) {
                int index = targetUrl.indexOf('=');
                builder.append(targetUrl.substring(0, index + 1));
            } else {
                builder.append(targetUrl).append(param);
            }
            builder.append(maxId);

            downloader.submitNewSearchTask(target, builder.toString());
        }

        List<DownloadTask> tasks = parseData(data);
        target.addTasks(tasks);
        downloader.addProgress(tasks.size());

        return tasks;
//        return parseData(data);
    }

    private List<DownloadTask> parseData(String data) throws IOException {
        /* Parse */
        /* Find all .jpg */
        List<DownloadTask> list = new ArrayList<>();
        String displaySrc = "(\"display_src\"):\"(https:[^\"]+)\"";
        Pattern pattern = Pattern.compile(displaySrc);
        Matcher matcher = pattern.matcher(data);

        while (matcher.find()) {
            String match = matcher.group(2).replace("\\", "");
            list.add(new DownloadTask(match, target));
        }

        return list;
    }

    private String checkForNextPage(String data) {
        /* Check next page flag */
        String checkPage = "\"has_next_page\":false";
        Pattern pattern = Pattern.compile(checkPage);
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            return null;
        }

        /* Get next page maxId */
        String endCursor = "(\"end_cursor\"):\"([^\"]+)\"";
        pattern = Pattern.compile(endCursor);
        matcher = pattern.matcher(data);

        if (matcher.find()) {
            String maxId = matcher.group(2);
            return maxId;
        }

        return null;
    }
}
