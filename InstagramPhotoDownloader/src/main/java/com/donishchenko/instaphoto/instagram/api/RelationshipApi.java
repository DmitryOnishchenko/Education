package com.donishchenko.instaphoto.instagram.api;

import com.donishchenko.instaphoto.instagram.data.RelationshipEnvelope;
import com.donishchenko.instaphoto.instagram.data.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class RelationshipApi {
    private static final String RELATIONSHIP_API = "/users";
    private String urlStart;
    private String urlEnd;

    public RelationshipApi(String urlStart, String urlEnd) {
        this.urlStart = urlStart + RELATIONSHIP_API;
        this.urlEnd = urlEnd;
    }

    /**
     * Get the list of users this user follows.
     * Parameters:
     * ACCESS_TOKEN - A valid access token.
     * @return
     * @throws ApiException
     */
    public List<User> getSelfFollows() throws ApiException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            URL url = new URL(urlStart + "/self/follows" + urlEnd);
            RelationshipEnvelope envelope = mapper.readValue(url, RelationshipEnvelope.class);

            return envelope.getData();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiException("Something going wrong in UserApi.getSelfFollows(). Contact the developer");
        }
    }

    /**
     * Get the list of users this user is followed by.
     * Parameters:
     * ACCESS_TOKEN - A valid access token.
     * @return
     * @throws ApiException
     */
    public List<User> getSelfFollowedBy() throws ApiException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            URL url = new URL(urlStart + "/self/followed-by" + urlEnd);
            RelationshipEnvelope envelope = mapper.readValue(url, RelationshipEnvelope.class);

            return envelope.getData();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiException("Something going wrong in UserApi.getSelf(). Contact the developer");
        }
    }
}
