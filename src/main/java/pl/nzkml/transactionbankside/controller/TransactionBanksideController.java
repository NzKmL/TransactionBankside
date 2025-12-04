package pl.nzkml.transactionbankside.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.nzkml.transactionbankside.dto.TransactionLogDto;
import pl.nzkml.transactionbankside.service.TransactionLogService;

@RestController
public class TransactionBanksideController {

    private final TransactionLogService transactionLogService;

    public TransactionBanksideController(TransactionLogService transactionLogService) {
        this.transactionLogService = transactionLogService;
    }

    @GetMapping("/transactionlog")
    public ResponseEntity<TransactionLogDto> getLogs(@RequestParam String transactionId) {
        return ResponseEntity.ok(transactionLogService.loadLogByTransactionId(transactionId));
    }

    @PostMapping("/transactionlog")
    public ResponseEntity<TransactionLogDto> saveLog(@RequestBody TransactionLogDto transactionLogDto){
        return ResponseEntity.ok(transactionLogService.createLog(transactionLogDto));
    }

}
