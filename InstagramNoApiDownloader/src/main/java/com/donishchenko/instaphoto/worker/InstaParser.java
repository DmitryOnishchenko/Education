package com.donishchenko.instaphoto.worker;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InstaParser {
    private InstaParser() {}

    public static String getData(String url) throws IOException {
        /* Get response */
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
            result.append('\n');
        }

        /* Search */
        String start = "static_root";

        int startIndex = result.indexOf(start);
        if (startIndex == -1) {
            throw new IOException("There is no such word. Connect the developer");
        }

        String end = "};</script>";
        int endIndex = result.indexOf(end);
        if (endIndex == -1) {
            throw new IOException("There is no such word. Connect the developer");
        }

        return "{" + result.substring(startIndex, endIndex + 1);
    }
}
