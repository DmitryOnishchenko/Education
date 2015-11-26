package com.donishchenko.instaphoto.instagram;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Meta {
    public static final String META_NAME = "meta";
    public static final String CODE_NAME = "code";
    public static final String ERROR_TYPE_NAME = "error_type";
    public static final String ERROR_MESSAGE_NAME = "error_message";

    @JsonProperty("code")
    private int code;

    @JsonProperty("error_type")
    private String errorType;

    @JsonProperty("error_message")
    private String errorMessage;

    public Meta() {}

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
