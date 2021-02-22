package com.game.kalah.interfaceadapters.controllers.bodies;

import org.springframework.hateoas.Link;

import java.net.URI;

public class CreateGameResponseBody {

    private String id;
    private URI uri;

    public CreateGameResponseBody(String id, URI uri) {
        this.id = id;
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public URI getUri() {
        return uri;
    }
}
