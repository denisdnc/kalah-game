package com.game.kalah.controllers.models;

import com.fasterxml.jackson.annotation.JsonCreator;

public class BusinessErrorResponseBody {
    private String message;

    @JsonCreator
    public BusinessErrorResponseBody(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
