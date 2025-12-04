package pl.nzkml.transactionbankside.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.nzkml.transactionbankside.model.SendingStatus;
import pl.nzkml.transactionbankside.model.Status;
import pl.nzkml.transactionbankside.model.TransactionLog;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionLogRepository extends CrudRepository<TransactionLog, Long> {
    List<TransactionLog> findByStatus(Status status);
    Optional<TransactionLog> findByTransactionId(String transactionId);
    List<TransactionLog> findBySendingStatus(SendingStatus sendingStatus);
}