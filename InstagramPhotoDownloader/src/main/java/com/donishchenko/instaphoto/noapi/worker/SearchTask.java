package com.donishchenko.instaphoto.noapi.worker;

import com.donishchenko.instaphoto.noapi.model.Target;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.donishchenko.instaphoto.noapi.logger.ConsoleLogger.log;

public class SearchTask implements Callable<List<DownloadTask>> {
    private Downloader downloader;
    private String mainUrl;
    private Target target;

    public SearchTask(Downloader downloader, String mainUrl, Target target) {
        this.downloader = downloader;
        this.mainUrl = mainUrl;
        this.target = target;
    }

    @Override
    public List<DownloadTask> call() throws Exception {
        String targetUrl = mainUrl + "/" + target.getName();

        log("Try to connect to: " + target.getName());

        long start = System.currentTimeMillis();

        int totalWork = getTotalWork(targetUrl);
        if (totalWork == -1) {
            return Collections.emptyList();
        }
        downloader.addWork(totalWork);

        List<DownloadTask> list = process(targetUrl);

        long elapsed = System.currentTimeMillis() - start;

        log(target.getName() + " :: Success in " + elapsed / 1000d + " sec. Total: " + list.size());

        return list;
    }

    private int getTotalWork(String url) throws IOException {
        String response = getResponse(url);
        String data = extractData(response);

        String mediaCount = "\"media\":\\{\"count\":([0-9]+)";
        Pattern pattern = Pattern.compile(mediaCount);
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        return -1;
    }

    private List<DownloadTask> process(String url) throws IOException {
        String response = getResponse(url);
        String data = extractData(response);

        List<DownloadTask> list = parseData(data);

        String maxId = checkForNextPage(data);
        if (maxId != null) {
            String param = "/?max_id=";
            String newUrl = mainUrl + "/" + target.getName() + param + maxId;

            List<DownloadTask> newList = process(newUrl);
            list.addAll(newList);
        }

        return list;
    }

    private String getResponse(String url) throws IOException {
//        log("Try to connect to: " + target);

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);

//        log("Response received, code : " + response.getStatusLine().getStatusCode());

//        log("Try extract data...");

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
            result.append('\n');
        }

        return result.toString();
    }

    private String extractData(String response) throws IOException {
        /* Search */
        String start = "static_root";

        int startIndex = response.indexOf(start);
        if (startIndex == -1) {
            throw new IOException("There is no such word. Connect the developer");
        }

        String end = "};</script>";
        int endIndex = response.indexOf(end);
        if (endIndex == -1) {
            throw new IOException("There is no such word. Connect the developer");
        }

        String data = "{" + response.substring(startIndex, endIndex + 1);

//        log("Success\n");

        return data;
    }

    private List<DownloadTask> parseData(String data) throws IOException {
        /* Parse */
        /* Find all .jpg */
        List<DownloadTask> list = new ArrayList<>();
        int counter = 0;
        String displaySrc = "(\"display_src\"):\"(https:[^\"]+)\"";
        Pattern pattern = Pattern.compile(displaySrc);
        Matcher matcher = pattern.matcher(data);

        while (matcher.find()) {
            counter += 1;

            String match = matcher.group(2).replace("\\", "");
            list.add(new DownloadTask(match, target));
//            log(match);
        }

//        log("Total: " + counter + "\n");

        return list;
    }

    private String checkForNextPage(String data) {
        /* Check next page */
        String checkPage = "\"has_next_page\":false";
        Pattern pattern = Pattern.compile(checkPage);
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
//            log("End");
            return null;
        }

        /* Go next page */
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
