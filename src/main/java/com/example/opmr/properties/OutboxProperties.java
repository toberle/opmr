package com.example.opmr.properties;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "outbox")
public class OutboxProperties {

    @Min(100)
    @Max(3600000)
    private int pollDelayMs;

    @Min(1)
    @Max(1000)
    private int queryLimit;

    @NotBlank
    private String topic;

    public int getPollDelayMs() {
        return pollDelayMs;
    }

    public void setPollDelayMs(int pollDelayMs) {
        this.pollDelayMs = pollDelayMs;
    }

    public int getQueryLimit() {
        return queryLimit;
    }

    public void setQueryLimit(int queryLimit) {
        this.queryLimit = queryLimit;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
