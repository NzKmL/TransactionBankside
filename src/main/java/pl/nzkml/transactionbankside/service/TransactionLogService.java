package pl.nzkml.transactionbankside.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import pl.nzkml.transactionbankside.dto.TransactionLogDto;
import pl.nzkml.transactionbankside.mapper.TransactionLogMapper;
import pl.nzkml.transactionbankside.model.SendingStatus;
import pl.nzkml.transactionbankside.model.TransactionLog;
import pl.nzkml.transactionbankside.repository.TransactionLogRepository;
import pl.nzkml.transactionbankside.transactionLog.TransactionLogSenderService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionLogService {

    private final TransactionLogRepository transactionLogRepository;
    private final TransactionLogMapper transactionLogMapper;
    private final TransactionLogSenderService transactionLogSenderService;
    @CacheEvict
    public TransactionLogDto createLog(TransactionLogDto transactionLogDTO) {
        TransactionLog transactionLog = transactionLogMapper.toEntity(transactionLogDTO);
        transactionLog =  transactionLogRepository.save(transactionLog);

        return transactionLogSenderService.sendLog(transactionLogMapper.toDto(transactionLog)).getBody();
    }

    public List<TransactionLog> loadLogsBySendingStatus(SendingStatus status) {
        return transactionLogRepository.findBySendingStatus(status);
    }

    public TransactionLogDto loadLogByTransactionId(String transactionId) {
        return transactionLogRepository.findByTransactionId(transactionId)
                .map(transactionLogMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Transaction log not found with id: " + transactionId));
    }
}