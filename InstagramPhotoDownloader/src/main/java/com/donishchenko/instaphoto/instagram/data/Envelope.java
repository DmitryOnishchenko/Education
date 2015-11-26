package com.donishchenko.instaphoto.instagram.data;

import com.donishchenko.instaphoto.instagram.Meta;
import com.donishchenko.instaphoto.instagram.Pagination;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Envelope {
    @JsonProperty("meta")
    private Meta meta;

    @JsonProperty("pagination")
    private Pagination pagination;

    public Envelope() {}

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
