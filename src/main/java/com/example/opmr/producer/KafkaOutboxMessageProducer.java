package com.example.opmr.producer;

import com.example.opmr.exception.OutboxMessageSendException;
import com.example.opmr.message.OutboxMessage;
import com.example.opmr.message.OutboxMessageResponseMetadata;
import com.example.opmr.properties.OutboxProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.concurrent.ExecutionException;

@Component
public class KafkaOutboxMessageProducer implements OutboxMessageProducer {

    private final KafkaTemplate<String, OutboxMessage> kafkaTemplate;
    private final OutboxProperties outboxProperties;

    public KafkaOutboxMessageProducer(KafkaTemplate<String, OutboxMessage> kafkaTemplate, OutboxProperties outboxProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.outboxProperties = outboxProperties;
    }

    @Override
    public OutboxMessageResponseMetadata send(OutboxMessage message) {
        try {
            SendResult<String, OutboxMessage> result = kafkaTemplate
                    .send(outboxProperties.getTopic(), message.getAggregateId(), message)
                    .get();
            Instant timestamp = extractTimestamp(result);
            String metadata = extractMetadata(result);
            return new OutboxMessageResponseMetadata(message.getId(), timestamp, metadata);
        } catch (InterruptedException | ExecutionException e) {
            throw new OutboxMessageSendException(e);
        }
    }

    private String extractMetadata(SendResult<String, OutboxMessage> result) {
        return MessageFormat.format("topic: {0}, partition: {1}, offset: {2}",
                result.getRecordMetadata().topic(), result.getRecordMetadata().partition(),
                result.getRecordMetadata().offset());
    }

    private Instant extractTimestamp(SendResult<String, OutboxMessage> stringSendResult) {
        if (stringSendResult.getRecordMetadata().hasTimestamp()) {
           return Instant.ofEpochMilli(stringSendResult.getRecordMetadata().timestamp());
        } else {
            return Instant.now();
        }
    }
}
