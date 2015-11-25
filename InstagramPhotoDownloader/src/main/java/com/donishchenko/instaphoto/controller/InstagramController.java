package com.donishchenko.instaphoto.controller;

import org.jinstagram.Instagram;
import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.jinstagram.entity.users.basicinfo.UserInfoData;
import org.jinstagram.exceptions.InstagramException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;

@Controller
@RequestMapping("/instagram/")
public class InstagramController {
    @Autowired
    private ServletContext servletContext;

    private Instagram instagram;

    @RequestMapping(value = "/getuser", method = RequestMethod.GET)
    public String getUser() throws InstagramException {
        instagram = (Instagram) servletContext.getAttribute("instagram");

        // User test
        System.out.println("============= User test ===============");
        String userId = "2292276196";

        UserInfo userInfo = null;
        try {
            userInfo = instagram.getUserInfo(userId);
        } catch (InstagramException e) {
            e.printStackTrace();
        }

        UserInfoData userData = userInfo.getData();
        System.out.println("id : " + userData.getId());
        System.out.println("first_name : " + userData.getFirstName());
        System.out.println("last_name : " + userData.getLastName());
        System.out.println("profile_picture : " + userData.getProfilePicture());
        System.out.println("website : " + userData.getWebsite());
        System.out.println(userData.getUsername());

        System.out.println("\n\n");


        return "home";
    }
}
