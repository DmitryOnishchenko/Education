package com.donishchenko.instaphoto.instagram;

import com.donishchenko.instaphoto.instagram.api.RelationshipApi;
import com.donishchenko.instaphoto.instagram.api.UserApi;

public class Instagram {
    private String urlStart = "https://api.instagram.com/v1";
    private String urlEnd = "?access_token=";
    private String accessToken;

    private UserApi userApi;
    private RelationshipApi relationshipApi;

    public Instagram(String accessToken) {
        this.accessToken = accessToken;
        this.urlEnd += accessToken;

        init();
    }

    private void init() {
        userApi = new UserApi(urlStart, urlEnd);
        relationshipApi = new RelationshipApi(urlStart, urlEnd);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public UserApi getUserApi() {
        return userApi;
    }

    public RelationshipApi getRelationshipApi() {
        return relationshipApi;
    }
}
