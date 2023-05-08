package com.example.opmr.message;

import java.time.Instant;

public class OutboxMessageResponseMetadata {

    private String id;
    private Instant timestamp;
    private String metadata;

    public OutboxMessageResponseMetadata() {
    }

    public OutboxMessageResponseMetadata(String id, Instant timestamp, String metadata) {
        this.id = id;
        this.timestamp = timestamp;
        this.metadata = metadata;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "OutboxMessageResponseMetadata{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", metadata='" + metadata + '\'' +
                '}';
    }
}
