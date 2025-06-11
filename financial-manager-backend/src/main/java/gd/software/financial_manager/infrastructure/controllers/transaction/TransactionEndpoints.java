package gd.software.financial_manager.infrastructure.controllers.transaction;

import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.domain.usecase.transaction.CreateTransaction;
import gd.software.financial_manager.domain.usecase.transaction.DeleteTransaction;
import gd.software.financial_manager.domain.usecase.transaction.FetchTransaction;
import gd.software.financial_manager.infrastructure.converts.DtoToTransaction;
import gd.software.financial_manager.infrastructure.converts.TransactionToDTO;
import gd.software.financial_manager.infrastructure.converts.TransactionToResponse;
import gd.software.financial_manager.infrastructure.dtos.TransactionDTO;
import gd.software.financial_manager.infrastructure.dtos.TransactionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(TransactionEndpoints.class);

    @Autowired
    private CreateTransaction createTransaction;

    @Autowired
    private FetchTransaction fetchTransaction;

    @Autowired
    private DeleteTransaction deleteTransaction;

    @PostMapping
    public ResponseEntity<TransactionDTO> save(@RequestBody TransactionDTO transactionDTO) {
        Transaction transaction = DtoToTransaction.convert(transactionDTO);
        Transaction savedTransaction = createTransaction.use(transaction);

        logger.info("Created transaction {}.", savedTransaction.name());

        return ResponseEntity.status(HttpStatus.CREATED).body(TransactionToDTO.convert(savedTransaction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<TransactionResponse>> fetchAllTransactions(@PathVariable UUID id) {
        List<Transaction> transactions = fetchTransaction.all(id);

        List<TransactionResponse> responses = TransactionToResponse.convert(transactions);

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable UUID id) {
        deleteTransaction.remove(id);
        logger.info("Removed transaction with id {}.", id);

        return ResponseEntity.noContent().build();
    }
}
