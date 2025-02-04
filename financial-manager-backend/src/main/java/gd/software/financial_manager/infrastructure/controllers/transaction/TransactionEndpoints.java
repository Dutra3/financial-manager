package gd.software.financial_manager.infrastructure.controllers.transaction;

import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.domain.usecase.transaction.FetchTransaction;
import gd.software.financial_manager.infrastructure.converts.TransactionToResponse;
import gd.software.financial_manager.infrastructure.dtos.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionEndpoints {

    @Autowired
    private FetchTransaction fetchTransaction;

    @GetMapping("/{id}")
    public ResponseEntity<List<TransactionResponse>> fetchAllTransactions(@PathVariable UUID id) {
        List<Transaction> transactions = fetchTransaction.all(id);

        List<TransactionResponse> responses = TransactionToResponse.convert(transactions);

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}
