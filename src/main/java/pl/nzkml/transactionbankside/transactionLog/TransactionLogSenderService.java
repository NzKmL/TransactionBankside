package pl.nzkml.transactionbankside.transactionLog;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import pl.nzkml.transactionbankside.dto.TransactionLogDto;

@Service
public class TransactionLogSenderService {

    private final RestClient restClient;

    public TransactionLogSenderService(RestClient.Builder restClientBuilder, @Value("${transaction.log.api.url}") String apiUrl) {
        this.restClient = restClientBuilder.baseUrl(apiUrl).build();
    }

    public ResponseEntity<TransactionLogDto> getLog(String transactionId) {
        return restClient.get()
                .uri("/api/transactions/transactionlog?transactionId={transactionId}", transactionId)
                .retrieve()
                .toEntity(TransactionLogDto.class);
    }

    public ResponseEntity<TransactionLogDto> sendLog(TransactionLogDto transactionLogDTO) {
        return restClient.post()
                .uri("/api/transactions/transactionlog")
                .contentType(MediaType.APPLICATION_JSON)
                .body(transactionLogDTO)
                .retrieve()
                .toEntity(TransactionLogDto.class);
    }
}