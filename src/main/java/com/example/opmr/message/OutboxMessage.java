package com.example.opmr.message;

public class OutboxMessage {

    private String id;
    private String type;
    private String aggregateType;
    private String aggregateId;
    private String payload;

    public OutboxMessage() {
    }

    public OutboxMessage(String id, String type, String aggregateType, String aggregateId, String payload) {
        this.id = id;
        this.type = type;
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.payload = payload;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public void setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "OutboxMessage{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", aggregateType='" + aggregateType + '\'' +
                ", aggregateId='" + aggregateId + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }
}
