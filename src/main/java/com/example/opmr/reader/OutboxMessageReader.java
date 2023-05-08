package com.example.opmr.reader;

import com.example.opmr.message.OutboxMessage;

import java.util.List;

public interface OutboxMessageReader {

    List<OutboxMessage> read();
}
