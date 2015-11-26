package com.donishchenko.instaphoto.controller;

import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/instagram/")
public class InstagramAuthContoller {
    @Autowired
    private ServletContext servletContext;

    private static String CLIENT_ID = "863e53e6626a4936937d033edc5b26f6";
    private static String CLIENT_SECRET = "0291885f0c614254832b17dd3d5bc4f2";
    private static String CALLBACK = "http://localhost:8080/instagram/callback";
    private static String SCOPE = "public_content follower_list comments relationships likes";

    private static final Token EMPTY_TOKEN = null;
    private InstagramService service;

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public String getAuth() {
        service = new InstagramAuthService()
                .apiKey(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .callback(CALLBACK)
                .scope(SCOPE)
                .build();

        String authUrl = service.getAuthorizationUrl(EMPTY_TOKEN);

        return "redirect:" + authUrl;
    }

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public String getCallback(HttpServletRequest request) {
        String code = request.getParameter("code");

        Verifier verifier = new Verifier(code);
        Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);

        servletContext.setAttribute("accessToken", accessToken.getToken());

        return "redirect:/";
    }
}
