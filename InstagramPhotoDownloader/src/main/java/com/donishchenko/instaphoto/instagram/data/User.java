package com.donishchenko.instaphoto.instagram.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty("id")
    private long id;

    @JsonProperty("username")
    private String userName;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("profile_picture")
    private String profilePictureUrl;

    @JsonProperty("bio")
    private String bio;

    @JsonProperty("website")
    private String website;

    @JsonProperty("counts")
    private UserCounts userCounts;

    public User() {}

    public User(long id, String username, String fullName, String profilePictureUrl, String bio, String website, UserCounts userCounts) {
        this.id = id;
        this.userName = username;
        this.fullName = fullName;
        this.profilePictureUrl = profilePictureUrl;
        this.bio = bio;
        this.website = website;
        this.userCounts = userCounts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public UserCounts getUserCounts() {
        return userCounts;
    }

    public void setUserCounts(UserCounts userCounts) {
        this.userCounts = userCounts;
    }
}
