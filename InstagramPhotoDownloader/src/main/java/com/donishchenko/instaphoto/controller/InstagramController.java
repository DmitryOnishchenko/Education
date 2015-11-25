package com.donishchenko.instaphoto.controller;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;
import java.io.IOException;

@Controller
@RequestMapping("/instagram/")
public class InstagramController {
    @Autowired
    private ServletContext servletContext;

//    private Instagram instagram;
    private String accessToken;

    @RequestMapping(value = "/getuser", method = RequestMethod.GET)
    public String getUser() {
//        instagram = (Instagram) servletContext.getAttribute("instagram");
        accessToken = (String) servletContext.getAttribute("accessToken");

        String name = "sacr8tum";
        String url = "https://api.instagram.com/v1/users/search?q=" + name + "&access_token=" + accessToken;


        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet(url);
//            request.addHeader("content-type", "application/json");
//            request.setEntity(params);
            //httpClient.execute(request);
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");

            try {
                JSONParser parser = new JSONParser();
                Object resultObject = parser.parse(json);

                if (resultObject instanceof JSONArray) {
                    JSONArray array=(JSONArray)resultObject;
                    for (Object object : array) {
                        JSONObject obj =(JSONObject)object;
                        System.out.println(obj.get("example"));
                        System.out.println(obj.get("fr"));
                    }

                }else if (resultObject instanceof JSONObject) {
                    JSONObject obj =(JSONObject)resultObject;

                    JSONArray data = (JSONArray) obj.get("data");

                    JSONObject fullName = (JSONObject) data.get(0);

                    System.out.println(fullName.get("full_name"));

                    System.out.println(obj.get("data"));
                    System.out.println(obj.get("fr"));
                }

            } catch (Exception e) {
                // TODO: handle exception
            }

        } catch (IOException ex) {
        }

        return "home";
    }
}
