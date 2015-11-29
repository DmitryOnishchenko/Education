package com.donishchenko.instaphoto.noapi.controller;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.donishchenko.instaphoto.noapi.logger.ConsoleLogger.log;

public class MainController {
    private Config config = new Config();

    public void startWork() {
        if (!config.checkConfig()) {
            log("Config not prepared.");
            return;
        }

//        String instagramUrl = "https://www.instagram.com";
//        String targetAccount = "ideas_tatoo";
//
//        String targetUrl = instagramUrl + "/" + targetAccount;
//
//        process(targetUrl);
    }

    private void process(String target) throws IOException {
        String instagramUrl = "https://www.instagram.com";
        String targetAccount = "ideas_tatoo";

        log("Try to connect to: " + target);

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(target);
        HttpResponse response = client.execute(request);

        log("Response received, code : " + response.getStatusLine().getStatusCode());

        log("Try extract data...");

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
            result.append('\n');
        }

        /* Search */
        String start = "static_root";

        int startIndex = result.indexOf(start);
        if (startIndex == -1) {
            log("There is no such word. Connect the developer");
            return;
        }

        String end = "};</script>";
        int endIndex = result.indexOf(end);
        if (endIndex == -1) {
            log("There is no such word. Connect the developer");
            return;
        }

        String data = "{" + result.substring(startIndex, endIndex + 1);

        log("Success\n");

        /* Parse */
        /* Find all .jpg */
        int counter = 0;
        String displaySrc = "(\"display_src\"):\"(https:[^\"]+)\"";
        Pattern pattern = Pattern.compile(displaySrc);
        Matcher matcher = pattern.matcher(data);

        while (matcher.find()) {
            counter += 1;

            String match = matcher.group(2).replace("\\", "");

            log(match);
        }

        log("Total: " + counter + "\n");

        /* Check next page */
        String checkPage = "\"has_next_page\":false";
        pattern = Pattern.compile(checkPage);
        matcher = pattern.matcher(data);
        if (matcher.find()) {
            log("End");
            return;
        }

        /* Go next page */
        String param = "/?max_id=";
        String endCursor = "(\"end_cursor\"):\"([^\"]+)\"";
        pattern = Pattern.compile(endCursor);
        matcher = pattern.matcher(data);

        if (matcher.find()) {
            String maxId = matcher.group(2);
            String newTarget = instagramUrl + "/" + targetAccount + param + maxId;

            process(newTarget);
        }
    }
}
