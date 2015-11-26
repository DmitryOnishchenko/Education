package com.donishchenko.instaphoto.instagram.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserEnvelope extends Envelope {
    @JsonProperty("data")
    private User data;

    public UserEnvelope() {}

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
