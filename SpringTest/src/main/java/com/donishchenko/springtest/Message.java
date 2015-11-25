package com.donishchenko.springtest;

public class Message {
    private String message;

    public Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
