package com.donishchenko.instaphoto.instagram.api;

import com.donishchenko.instaphoto.instagram.data.User;
import com.donishchenko.instaphoto.instagram.data.UserEnvelope;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class UserApi {
    private static final String USER_API = "/users";
    private String urlStart;
    private String urlEnd;

    public UserApi(String urlStart, String urlEnd) {
        this.urlStart = urlStart + USER_API;
        this.urlEnd = urlEnd;
    }

    /**
     * Get information about the owner of the access_token.
     * Parameters:
     * ACCESS_TOKEN - A valid access token.
     * @return
     * @throws ApiException
     */
    public User getSelf() throws ApiException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            URL url = new URL(urlStart + "/self/" + urlEnd);
            UserEnvelope envelope = mapper.readValue(url, UserEnvelope.class);

            return envelope.getData();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiException("Something going wrong in UserApi.getSelf(). Contact the developer");
        }
    }

    /**
     * Get information about a user.
     * This endpoint requires the public_content scope if the user-id is not the owner of the access_token.
     * Parameters:
     * ACCESS_TOKEN - A valid access token.
     * @param userId
     * @return
     * @throws ApiException
     */
    public User getById(long userId) throws ApiException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            URL url = new URL(urlStart + "/" + userId + "/" + urlEnd);
            UserEnvelope envelope = mapper.readValue(url, UserEnvelope.class);

            return envelope.getData();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiException("Something going wrong in UserApi.getById(). Contact the developer");
        }
    }

    /**
     * Get the most recent media published by the owner of the access_token.
     * Parameters:
     * ACCESS_TOKEN - A valid access token.
     * COUNT - Count of media to return.
     * MIN_ID - Return media later than this min_id.
     * MAX_ID - Return media earlier than this max_id.
     * @return
     */
    public static String getSelfRecentMedia() {
        return null;
    }

    /**
     * Get the most recent media published by a user.
     * This endpoint requires the public_content scope if the user-id is not the owner of the access_token.
     * Parameters:
     * ACCESS_TOKEN - A valid access token.
     * COUNT - Count of media to return.
     * MIN_ID - Return media later than this min_id.
     * MAX_ID - Return media earlier than this max_id.
     * @param userId
     * @return
     */
    public static String getUserRecentMedia(long userId) {
        return null;
    }

    /**
     * Get the list of recent media liked by the owner of the access_token.
     * Parameters:
     * ACCESS_TOKEN - A valid access token.
     * COUNT - Count of media to return.
     * MAX_LIKE_ID - Return media liked before this id.
     * @return
     */
    public static String getSelfRecentLiked() {
        return null;
    }

    /**
     * Get a list of users matching the query.
     * Parameters:
     * ACCESS_TOKEN - A valid access token.
     * Q - A query string.
     * COUNT - Number of users to return.
     * @param query
     * @return
     */
    public static List<User> search(String query) {
        return null;
    }
}
