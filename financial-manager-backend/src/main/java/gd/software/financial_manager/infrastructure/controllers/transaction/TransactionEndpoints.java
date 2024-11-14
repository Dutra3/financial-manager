package gd.software.financial_manager.infrastructure.controllers.transaction;

import gd.software.financial_manager.infrastructure.dtos.TransactionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionEndpoints {

    @GetMapping
    public ResponseEntity<TransactionResponse> fetchAllTransactions() {
        return null;
    }
}
