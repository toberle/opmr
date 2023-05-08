package com.example.opmr.processor;

import com.example.opmr.message.OutboxMessage;
import com.example.opmr.message.OutboxMessageResponseMetadata;
import com.example.opmr.producer.OutboxMessageProducer;
import com.example.opmr.reader.OutboxMessageReader;
import com.example.opmr.writer.OutboxMessageMetadataWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class OutboxMessageProcessor {

    private static final Logger log = LoggerFactory.getLogger(OutboxMessageProcessor.class);

    private final OutboxMessageReader reader;
    private final OutboxMessageMetadataWriter writer;
    private final OutboxMessageProducer producer;

    public OutboxMessageProcessor(OutboxMessageReader reader, OutboxMessageMetadataWriter writer, OutboxMessageProducer producer) {
        this.reader = reader;
        this.writer = writer;
        this.producer = producer;
    }

    @Scheduled(fixedDelayString = "${outbox.poll-delay-ms}")
    public void processOutboxMessages() {
        MDC.put("processing-id", UUID.randomUUID().toString());
        log.info("Processing of outbox messages started");
        List<OutboxMessage> messages = reader.read();
        log.info("Processing outbox messages: {}", messages.size());
        List<OutboxMessageResponseMetadata> resultMetadata = sendMessages(messages);
        log.info("Processing outbox messages result: {}", messages.size());
        writeResponseToOutbox(resultMetadata);
        log.info("Processing of outbox messages finished");
    }

    private void writeResponseToOutbox(List<OutboxMessageResponseMetadata> resultMetadata) {
        try {
            writer.writeMetadata(resultMetadata);
        } catch (Exception e) {
            log.error("Error while writing response metadata to outbox", e);
        }
    }

    private List<OutboxMessageResponseMetadata> sendMessages(List<OutboxMessage> messages) {
        List<OutboxMessageResponseMetadata> resultMetadata = new ArrayList<>();
        for (OutboxMessage message : messages) {
            try {
                resultMetadata.add(producer.send(message));
            } catch (Exception e) {
                log.error("Error while sending message with ID: {}", message.getId(), e);
            }
        }
        return resultMetadata;
    }
}
