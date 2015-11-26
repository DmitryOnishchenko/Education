package com.donishchenko.instaphoto.controller;

import com.donishchenko.instaphoto.instagram.Instagram;
import com.donishchenko.instaphoto.instagram.api.ApiException;
import com.donishchenko.instaphoto.instagram.api.UserApi;
import com.donishchenko.instaphoto.instagram.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/instagram/")
public class InstagramController {
    @Autowired
    private ServletContext servletContext;

    private String accessToken;

    @RequestMapping(value = "/getuser", method = RequestMethod.GET)
    public String getUser() throws IOException, ApiException {
        accessToken = (String) servletContext.getAttribute("accessToken");

        Instagram instagram = new Instagram(accessToken);
        UserApi userApi = instagram.getUserApi();

        User user = userApi.getSelf();
        long userId = user.getId();

        User newUser = instagram.getUserApi().getById(userId);

        List<User> list = instagram.getRelationshipApi().getSelfFollows();
        List<User> list2 = instagram.getRelationshipApi().getSelfFollowedBy();

        return "home";
    }
}
