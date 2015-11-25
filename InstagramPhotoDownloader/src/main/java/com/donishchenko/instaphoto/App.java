package com.donishchenko.instaphoto;

import org.jinstagram.Instagram;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class App {
    private static final Token EMPTY_TOKEN = null;
    private static final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws IOException {
        InstagramService service = new InstagramAuthService()
                .apiKey("863e53e6626a4936937d033edc5b26f6")
                .apiSecret("0291885f0c614254832b17dd3d5bc4f2")
                .callback("http://localhost")
                .scope("public_content")
                .build();


        String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
        HttpURLConnection.setFollowRedirects(false);
        HttpURLConnection connection = (HttpURLConnection) new URL(authorizationUrl).openConnection();
        connection.setInstanceFollowRedirects(false);

        int responseCode = connection.getResponseCode();
        String location = "";
        if (responseCode == 302) {
            location = connection.getHeaderField("Location");
        }

        HttpURLConnection connection2 = (HttpURLConnection) new URL(location).openConnection();
        connection2.setInstanceFollowRedirects(false);

        responseCode = connection2.getResponseCode();

        Map<String, List<String>> headerFields = connection2.getHeaderFields();
        Set<String> headerFieldsSet = headerFields.keySet();
        Iterator<String> hearerFieldsIter = headerFieldsSet.iterator();

        String token = "";
        while (hearerFieldsIter.hasNext()) {
            String headerFieldKey = hearerFieldsIter.next();

            if ("Set-Cookie".equalsIgnoreCase(headerFieldKey)) {
                List<String> headerFieldValue = headerFields.get(headerFieldKey);

                for (String headerValue : headerFieldValue) {
                    String[] fields = headerValue.split(";\\s*");

                    if (fields[0].startsWith("csrftoken")) {
                        int pos = fields[0].indexOf('=');

                        token = fields[0].substring(pos + 1);
                    }
                }
            }
        }

//        String location2 = "";
//        if (responseCode == 301) {
//            location2 = connection.getHeaderField("Location");
//        }
//
//        HttpURLConnection connection3 = (HttpURLConnection) new URL(location2).openConnection();
//        connection3.setInstanceFollowRedirects(false);
//
//        responseCode = connection3.getResponseCode();
//        String location3 = "";
//        if (responseCode == 301) {
//            location3 = connection.getHeaderField("Location");
//        }
//
//        HttpURLConnection connection4 = (HttpURLConnection) new URL(location3).openConnection();
//        connection4.setInstanceFollowRedirects(false);
//
//        responseCode = connection4.getResponseCode();
//        String location4 = "";
//        if (responseCode == 301) {
//            location4 = connection.getHeaderField("Location");
//        }


//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        HttpClientContext context = HttpClientContext.create();
//        HttpGet request = new HttpGet(authorizationUrl);
//        CloseableHttpResponse response = httpclient.execute(request, context);
//
//        CookieStore cookieStore = context.getCookieStore();
//        List<Cookie> cookies = cookieStore.getCookies();
//        String token = "";
//        for (Cookie cookie : cookies) {
//            if (cookie.getName().equals("csrftoken")) {
//                token = cookie.getValue();
//            }
//        }

        Verifier verifier = new Verifier(token);
        Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);

        Instagram instagram = new Instagram(accessToken);
    }
}
