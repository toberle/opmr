package com.example.opmr.producer;

import com.example.opmr.message.OutboxMessage;
import com.example.opmr.message.OutboxMessageResponseMetadata;

public interface OutboxMessageProducer {

    OutboxMessageResponseMetadata send(OutboxMessage outboxMessage);
}
