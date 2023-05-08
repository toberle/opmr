package com.example.opmr.reader;

import com.example.opmr.message.OutboxMessage;
import com.example.opmr.properties.OutboxProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RdbmsOutboxMessageReader implements OutboxMessageReader {

    private final JdbcTemplate jdbcTemplate;
    private final OutboxProperties outboxProperties;

    public RdbmsOutboxMessageReader(JdbcTemplate jdbcTemplate, OutboxProperties outboxProperties) {
        this.jdbcTemplate = jdbcTemplate;
        this.outboxProperties = outboxProperties;
    }

    @Override
    public List<OutboxMessage> read() {
        return jdbcTemplate.query("SELECT * FROM outbox WHERE sent_ts IS NULL ORDER BY created_ts LIMIT ?",
                preparedStatement -> preparedStatement.setInt(1, outboxProperties.getQueryLimit()),
                (resultSet, rowNum) -> new OutboxMessage(
                        resultSet.getString("id"),
                        resultSet.getString("type"),
                        resultSet.getString("aggregate_type"),
                        resultSet.getString("aggregate_id"),
                        resultSet.getString("payload")
                ));
    }
}
