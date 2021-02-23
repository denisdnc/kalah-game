package com.game.kalah.interfaceadapters.controllers.models;

import java.net.URI;
import java.util.Map;
import java.util.SortedMap;

public class GameStatusResponseBody {
    private String id;
    private URI uri;
    private SortedMap<String, String> status;

    public GameStatusResponseBody(String id, URI uri, SortedMap<String, String> status) {
        this.id = id;
        this.uri = uri;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public URI getUri() {
        return uri;
    }

    public Map<String, String> getStatus() {
        return status;
    }
}
