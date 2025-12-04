package pl.nzkml.transactionbankside.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.nzkml.transactionbankside.controller.TransactionBanksideController;
import pl.nzkml.transactionbankside.dto.TransactionLogDto;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TransactionLogServiceTest {

    @Autowired
    private TransactionBanksideController transactionBanksideController;

    @Test
    void shouldCreateTenTransactionLogs() {
        for (int i = 0; i < 10; i++) {
            // Given
            TransactionLogDto newLogDto = new TransactionLogDto(
                    UUID.randomUUID().toString(),
                    OffsetDateTime.now(),
                    TransactionLogDto.Status.INITIALIZED,
                    "Test transaction " + i
            );

            // When
            var createdLog = transactionBanksideController.saveLog(newLogDto);

            // Then
         //   assertNotNull(createdLog);
          //  assertNotNull(createdLog.getTransactionId());
        }
    }
}