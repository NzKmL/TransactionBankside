package pl.nzkml.transactionbankside.mapper;

import org.springframework.stereotype.Component;
import pl.nzkml.transactionbankside.dto.TransactionLogDto;
import pl.nzkml.transactionbankside.model.SendingStatus;
import pl.nzkml.transactionbankside.model.Status;
import pl.nzkml.transactionbankside.model.TransactionLog;

@Component
public class TransactionLogMapper {

    public TransactionLog toEntity(TransactionLogDto dto) {
        if (dto == null) {
            return null;
        }

        return TransactionLog.builder()
                .transactionId(dto.getTransactionId())
                .timestamp(dto.getTimestamp())
                .status(toModelStatus(dto.getStatus()))
                .description(dto.getDescription())
                .sendingStatus(SendingStatus.TO_SEND) // Default value
                .build();
    }

    public TransactionLogDto toDto(TransactionLog entity) {
        if (entity == null) {
            return null;
        }

        return new TransactionLogDto(
                entity.getTransactionId(),
                entity.getTimestamp(),
                toDtoStatus(entity.getStatus()),
                entity.getDescription()
        );
    }

    private Status toModelStatus(TransactionLogDto.Status dtoStatus) {
        if (dtoStatus == null) {
            return null;
        }
        return Status.valueOf(dtoStatus.name());
    }

    private TransactionLogDto.Status toDtoStatus(Status modelStatus) {
        if (modelStatus == null) {
            return null;
        }
        return TransactionLogDto.Status.valueOf(modelStatus.name());
    }
}