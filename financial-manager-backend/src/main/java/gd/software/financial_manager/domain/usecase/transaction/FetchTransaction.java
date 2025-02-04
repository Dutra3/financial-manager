package gd.software.financial_manager.domain.usecase.transaction;

import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.domain.usecase.collections.AllTransactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class FetchTransaction {

    private static final Logger logger = LoggerFactory.getLogger(FetchTransaction.class);

    private final AllTransactions allTransactions;

    public FetchTransaction(AllTransactions allTransactions) {
        this.allTransactions = allTransactions;
    }

    public List<Transaction> all(UUID id) {
        logger.info("Fetch all transactions for user {}.", id);
        return allTransactions.allBy(id);
    }
}
