package pl.nzkml.transactionbankside.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Setter
@Getter
public class TransactionLogDto {
    private String transactionId;
    private OffsetDateTime timestamp;
    private Status status;
    private String description;

    public TransactionLogDto() {
    }

    public TransactionLogDto(String transactionId, OffsetDateTime timestamp, Status status, String description) {
        this.transactionId = transactionId;
        this.timestamp = timestamp;
        this.status = status;
        this.description = description;
    }

    public enum Status {
        INITIALIZED,
        PROCESSING,
        COMPLETED
    }
}