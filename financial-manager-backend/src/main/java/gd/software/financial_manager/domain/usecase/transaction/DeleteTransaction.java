package gd.software.financial_manager.domain.usecase.transaction;

import gd.software.financial_manager.domain.usecase.collections.AllTransactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteTransaction {

    private static final Logger logger = LoggerFactory.getLogger(DeleteTransaction.class);

    private final AllTransactions allTransactions;

    public DeleteTransaction(AllTransactions allTransactions) {
        this.allTransactions = allTransactions;
    }

    public void remove(UUID id) {
        allTransactions.remove(id);
    }
}
