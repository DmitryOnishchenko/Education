package com.donishchenko.instaphoto.instagram.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserCounts {
    @JsonProperty("media")
    private long media;

    @JsonProperty("follows")
    private long follows;

    @JsonProperty("followed_by")
    private long followedBy;

    public UserCounts() {}

    public UserCounts(long media, long follows, long followedBy) {
        this.media = media;
        this.follows = follows;
        this.followedBy = followedBy;
    }

    public long getMedia() {
        return media;
    }

    public void setMedia(long media) {
        this.media = media;
    }

    public long getFollows() {
        return follows;
    }

    public void setFollows(long follows) {
        this.follows = follows;
    }

    public long getFollowedBy() {
        return followedBy;
    }

    public void setFollowedBy(long followedBy) {
        this.followedBy = followedBy;
    }
}
