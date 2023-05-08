package com.example.opmr.writer;

import com.example.opmr.message.OutboxMessageResponseMetadata;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Component
public class RdbmsOutboxMessageMetadataWriter implements OutboxMessageMetadataWriter {

    private final JdbcTemplate jdbcTemplate;

    public RdbmsOutboxMessageMetadataWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void writeMetadata(List<OutboxMessageResponseMetadata> resultMetadata) {
        jdbcTemplate.batchUpdate("UPDATE outbox SET send_ts = ?, metadata = ? WHERE id = ?",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setTimestamp(1, Timestamp.from(resultMetadata.get(i).getTimestamp()));
                        ps.setString(2, resultMetadata.get(i).getMetadata());
                        ps.setString(3, resultMetadata.get(i).getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return resultMetadata.size();
                    }
                });
    }
}
