package com.example.opmr.writer;

import com.example.opmr.message.OutboxMessageResponseMetadata;

import java.util.List;

public interface OutboxMessageMetadataWriter {

    void writeMetadata(List<OutboxMessageResponseMetadata> resultMetadata);
}
