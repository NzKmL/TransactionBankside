package pl.nzkml.transactionbankside.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import pl.nzkml.transactionbankside.dto.TransactionLogDto;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TransactionBanksideControllerTest {

    @Autowired
    private TransactionBanksideController transactionBanksideController;

    @Test
    void shouldCreateAndProcessTransactionLogs() {
        // A predefined, readable, and shuffled list of 100 test cases for 10 transactions
        List<TransactionLogDto> testCases = getPredefinedTestCases();

        // Send all logs from the predefined list
        testCases.forEach(log -> {
            ResponseEntity<TransactionLogDto> response = transactionBanksideController.saveLog(log);
            TransactionLogDto createdLog = response.getBody();

            assertNotNull(createdLog);
            assertNotNull(createdLog.getTransactionId());
        });
    }

    private List<TransactionLogDto> getPredefinedTestCases() {
        // 10 real 32-character UUIDs
        final String id1 = "a1b3c4d5e6f7a1b3c4d5e6f7a1b3c4d5";
        final String id2 = "b2c4d5e6f7a1b3c4d5e6f7a1b3c4d5e6";
        final String id3 = "c3d5e6f7a1b3c4d5e6f7a1b3c4d5e6f7";
        final String id4 = "d4e6f7a1b3c4d5e6f7a1b3c4d5e6f7a1";
        final String id5 = "e5f7a1b3c4d5e6f7a1b3c4d5e6f7a1b3";
        final String id6 = "f6a1b3c4d5e6f7a1b3c4d5e6f7a1b3c4";
        final String id7 = "a7b3c4d5e6f7a1b3c4d5e6f7a1b3c4d5";
        final String id8 = "b8c4d5e6f7a1b3c4d5e6f7a1b3c4d5e6";
        final String id9 = "c9d5e6f7a1b3c4d5e6f7a1b3c4d5e6f7";
        final String id10 = "d1e6f7a1b3c4d5e6f7a1b3c4d5e6f7a1";

        return List.of(
            // Block of INITIALIZED logs - they appear first for their respective IDs
            new TransactionLogDto(id7, OffsetDateTime.parse("2024-08-01T16:00:00+02:00"), TransactionLogDto.Status.INITIALIZED, "Event 7-0"),
            new TransactionLogDto(id1, OffsetDateTime.parse("2024-08-01T10:00:00+02:00"), TransactionLogDto.Status.INITIALIZED, "Event 1-0"),
            new TransactionLogDto(id4, OffsetDateTime.parse("2024-08-01T13:00:00+02:00"), TransactionLogDto.Status.INITIALIZED, "Event 4-0"),
            new TransactionLogDto(id9, OffsetDateTime.parse("2024-08-01T18:00:00+02:00"), TransactionLogDto.Status.INITIALIZED, "Event 9-0"),
            new TransactionLogDto(id2, OffsetDateTime.parse("2024-08-01T11:00:00+02:00"), TransactionLogDto.Status.INITIALIZED, "Event 2-0"),
            new TransactionLogDto(id6, OffsetDateTime.parse("2024-08-01T15:00:00+02:00"), TransactionLogDto.Status.INITIALIZED, "Event 6-0"),
            new TransactionLogDto(id10, OffsetDateTime.parse("2024-08-01T19:00:00+02:00"), TransactionLogDto.Status.INITIALIZED, "Event 10-0"),
            new TransactionLogDto(id3, OffsetDateTime.parse("2024-08-01T12:00:00+02:00"), TransactionLogDto.Status.INITIALIZED, "Event 3-0"),
            new TransactionLogDto(id8, OffsetDateTime.parse("2024-08-01T17:00:00+02:00"), TransactionLogDto.Status.INITIALIZED, "Event 8-0"),
            new TransactionLogDto(id5, OffsetDateTime.parse("2024-08-01T14:00:00+02:00"), TransactionLogDto.Status.INITIALIZED, "Event 5-0"),

            // Block of PROCESSING and COMPLETED logs, shuffled
            new TransactionLogDto(id8, OffsetDateTime.parse("2024-08-01T17:05:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 8-5"),
            new TransactionLogDto(id1, OffsetDateTime.parse("2024-08-01T10:09:00+02:00"), TransactionLogDto.Status.COMPLETED, "Event 1-9"),
            new TransactionLogDto(id3, OffsetDateTime.parse("2024-08-01T12:01:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 3-1"),
            new TransactionLogDto(id5, OffsetDateTime.parse("2024-08-01T14:03:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 5-3"),
            new TransactionLogDto(id7, OffsetDateTime.parse("2024-08-01T16:08:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 7-8"),
            new TransactionLogDto(id9, OffsetDateTime.parse("2024-08-01T18:04:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 9-4"),
            new TransactionLogDto(id4, OffsetDateTime.parse("2024-08-01T13:06:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 4-6"),
            new TransactionLogDto(id6, OffsetDateTime.parse("2024-08-01T15:02:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 6-2"),
            new TransactionLogDto(id10, OffsetDateTime.parse("2024-08-01T19:07:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 10-7"),
            new TransactionLogDto(id1, OffsetDateTime.parse("2024-08-01T10:01:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 1-1"),
            new TransactionLogDto(id8, OffsetDateTime.parse("2024-08-01T17:02:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 8-2"),
            new TransactionLogDto(id5, OffsetDateTime.parse("2024-08-01T14:09:00+02:00"), TransactionLogDto.Status.COMPLETED, "Event 5-9"),
            new TransactionLogDto(id3, OffsetDateTime.parse("2024-08-01T12:05:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 3-5"),
            new TransactionLogDto(id7, OffsetDateTime.parse("2024-08-01T16:03:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 7-3"),
            new TransactionLogDto(id2, OffsetDateTime.parse("2024-08-01T11:04:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 2-4"),
            new TransactionLogDto(id6, OffsetDateTime.parse("2024-08-01T15:07:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 6-7"),
            new TransactionLogDto(id4, OffsetDateTime.parse("2024-08-01T13:01:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 4-1"),
            new TransactionLogDto(id10, OffsetDateTime.parse("2024-08-01T19:02:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 10-2"),
            new TransactionLogDto(id1, OffsetDateTime.parse("2024-08-01T10:06:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 1-6"),
            new TransactionLogDto(id8, OffsetDateTime.parse("2024-08-01T17:08:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 8-8"),
            new TransactionLogDto(id5, OffsetDateTime.parse("2024-08-01T14:02:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 5-2"),
            new TransactionLogDto(id3, OffsetDateTime.parse("2024-08-01T12:08:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 3-8"),
            new TransactionLogDto(id7, OffsetDateTime.parse("2024-08-01T16:06:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 7-6"),
            new TransactionLogDto(id9, OffsetDateTime.parse("2024-08-01T18:06:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 9-6"),
            new TransactionLogDto(id2, OffsetDateTime.parse("2024-08-01T11:07:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 2-7"),
            new TransactionLogDto(id4, OffsetDateTime.parse("2024-08-01T13:08:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 4-8"),
            new TransactionLogDto(id10, OffsetDateTime.parse("2024-08-01T19:09:00+02:00"), TransactionLogDto.Status.COMPLETED, "Event 10-9"),
            new TransactionLogDto(id1, OffsetDateTime.parse("2024-08-01T10:03:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 1-3"),
            new TransactionLogDto(id5, OffsetDateTime.parse("2024-08-01T14:05:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 5-5"),
            new TransactionLogDto(id3, OffsetDateTime.parse("2024-08-01T12:03:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 3-3"),
            new TransactionLogDto(id9, OffsetDateTime.parse("2024-08-01T18:01:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 9-1"),
            new TransactionLogDto(id2, OffsetDateTime.parse("2024-08-01T11:02:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 2-2"),
            new TransactionLogDto(id6, OffsetDateTime.parse("2024-08-01T15:05:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 6-5"),
            new TransactionLogDto(id4, OffsetDateTime.parse("2024-08-01T13:03:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 4-3"),
            new TransactionLogDto(id10, OffsetDateTime.parse("2024-08-01T19:04:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 10-4"),
            new TransactionLogDto(id1, OffsetDateTime.parse("2024-08-01T10:08:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 1-8"),
            new TransactionLogDto(id8, OffsetDateTime.parse("2024-08-01T17:06:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 8-6"),
            new TransactionLogDto(id3, OffsetDateTime.parse("2024-08-01T12:09:00+02:00"), TransactionLogDto.Status.COMPLETED, "Event 3-9"),
            new TransactionLogDto(id7, OffsetDateTime.parse("2024-08-01T16:05:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 7-5"),
            new TransactionLogDto(id9, OffsetDateTime.parse("2024-08-01T18:09:00+02:00"), TransactionLogDto.Status.COMPLETED, "Event 9-9"),
            new TransactionLogDto(id2, OffsetDateTime.parse("2024-08-01T11:09:00+02:00"), TransactionLogDto.Status.COMPLETED, "Event 2-9"),
            new TransactionLogDto(id6, OffsetDateTime.parse("2024-08-01T15:09:00+02:00"), TransactionLogDto.Status.COMPLETED, "Event 6-9"),
            new TransactionLogDto(id4, OffsetDateTime.parse("2024-08-01T13:09:00+02:00"), TransactionLogDto.Status.COMPLETED, "Event 4-9"),
            new TransactionLogDto(id8, OffsetDateTime.parse("2024-08-01T17:01:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 8-1"),
            new TransactionLogDto(id5, OffsetDateTime.parse("2024-08-01T14:07:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 5-7"),
            new TransactionLogDto(id7, OffsetDateTime.parse("2024-08-01T16:02:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 7-2"),
            new TransactionLogDto(id9, OffsetDateTime.parse("2024-08-01T18:03:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 9-3"),
            new TransactionLogDto(id2, OffsetDateTime.parse("2024-08-01T11:06:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 2-6"),
            new TransactionLogDto(id6, OffsetDateTime.parse("2024-08-01T15:04:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 6-4"),
            new TransactionLogDto(id4, OffsetDateTime.parse("2024-08-01T13:05:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 4-5"),
            new TransactionLogDto(id10, OffsetDateTime.parse("2024-08-01T19:06:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 10-6"),
            new TransactionLogDto(id1, OffsetDateTime.parse("2024-08-01T10:05:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 1-5"),
            new TransactionLogDto(id8, OffsetDateTime.parse("2024-08-01T17:09:00+02:00"), TransactionLogDto.Status.COMPLETED, "Event 8-9"),
            new TransactionLogDto(id5, OffsetDateTime.parse("2024-08-01T14:04:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 5-4"),
            new TransactionLogDto(id3, OffsetDateTime.parse("2024-08-01T12:07:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 3-7"),
            new TransactionLogDto(id7, OffsetDateTime.parse("2024-08-01T16:09:00+02:00"), TransactionLogDto.Status.COMPLETED, "Event 7-9"),
            new TransactionLogDto(id9, OffsetDateTime.parse("2024-08-01T18:08:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 9-8"),
            new TransactionLogDto(id2, OffsetDateTime.parse("2024-08-01T11:01:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 2-1"),
            new TransactionLogDto(id6, OffsetDateTime.parse("2024-08-01T15:08:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 6-8"),
            new TransactionLogDto(id10, OffsetDateTime.parse("2024-08-01T19:01:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 10-1"),
            new TransactionLogDto(id1, OffsetDateTime.parse("2024-08-01T10:02:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 1-2"),
            new TransactionLogDto(id8, OffsetDateTime.parse("2024-08-01T17:04:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 8-4"),
            new TransactionLogDto(id5, OffsetDateTime.parse("2024-08-01T14:08:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 5-8"),
            new TransactionLogDto(id3, OffsetDateTime.parse("2024-08-01T12:04:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 3-4"),
            new TransactionLogDto(id7, OffsetDateTime.parse("2024-08-01T16:04:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 7-4"),
            new TransactionLogDto(id9, OffsetDateTime.parse("2024-08-01T18:02:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 9-2"),
            new TransactionLogDto(id2, OffsetDateTime.parse("2024-08-01T11:08:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 2-8"),
            new TransactionLogDto(id6, OffsetDateTime.parse("2024-08-01T15:03:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 6-3"),
            new TransactionLogDto(id4, OffsetDateTime.parse("2024-08-01T13:07:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 4-7"),
            new TransactionLogDto(id10, OffsetDateTime.parse("2024-08-01T19:08:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 10-8"),
            new TransactionLogDto(id1, OffsetDateTime.parse("2024-08-01T10:07:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 1-7"),
            new TransactionLogDto(id8, OffsetDateTime.parse("2024-08-01T17:03:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 8-3"),
            new TransactionLogDto(id5, OffsetDateTime.parse("2024-08-01T14:01:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 5-1"),
            new TransactionLogDto(id3, OffsetDateTime.parse("2024-08-01T12:06:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 3-6"),
            new TransactionLogDto(id7, OffsetDateTime.parse("2024-08-01T16:01:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 7-1"),
            new TransactionLogDto(id9, OffsetDateTime.parse("2024-08-01T18:07:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 9-7"),
            new TransactionLogDto(id2, OffsetDateTime.parse("2024-08-01T11:03:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 2-3"),
            new TransactionLogDto(id6, OffsetDateTime.parse("2024-08-01T15:06:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 6-6"),
            new TransactionLogDto(id4, OffsetDateTime.parse("2024-08-01T13:02:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 4-2"),
            new TransactionLogDto(id10, OffsetDateTime.parse("2024-08-01T19:03:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 10-3"),
            new TransactionLogDto(id1, OffsetDateTime.parse("2024-08-01T10:04:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 1-4"),
            new TransactionLogDto(id8, OffsetDateTime.parse("2024-08-01T17:07:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 8-7"),
            new TransactionLogDto(id5, OffsetDateTime.parse("2024-08-01T14:06:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 5-6"),
            new TransactionLogDto(id3, OffsetDateTime.parse("2024-08-01T12:02:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 3-2"),
            new TransactionLogDto(id7, OffsetDateTime.parse("2024-08-01T16:07:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 7-7"),
            new TransactionLogDto(id9, OffsetDateTime.parse("2024-08-01T18:05:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 9-5"),
            new TransactionLogDto(id2, OffsetDateTime.parse("2024-08-01T11:05:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 2-5"),
            new TransactionLogDto(id6, OffsetDateTime.parse("2024-08-01T15:01:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 6-1"),
            new TransactionLogDto(id4, OffsetDateTime.parse("2024-08-01T13:04:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 4-4"),
            new TransactionLogDto(id10, OffsetDateTime.parse("2024-08-01T19:05:00+02:00"), TransactionLogDto.Status.PROCESSING, "Event 10-5")
        );
    }
}