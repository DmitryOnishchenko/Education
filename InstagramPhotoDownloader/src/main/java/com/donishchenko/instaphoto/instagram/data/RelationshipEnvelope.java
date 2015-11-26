package com.donishchenko.instaphoto.instagram.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RelationshipEnvelope extends Envelope {
    @JsonProperty("data")
    private List<User> data;

    public RelationshipEnvelope() {}

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}
