package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.ReitTransaction;
import gd.software.financial_manager.domain.usecase.collections.AllReitTransactions;
import gd.software.financial_manager.infrastructure.converts.ReitTransactionToRow;
import gd.software.financial_manager.infrastructure.converts.RowToReitTransaction;
import gd.software.financial_manager.infrastructure.persistence.relational.ReitTransactionRow;
import gd.software.financial_manager.infrastructure.persistence.repository.ReitTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllReitTransactionsPersistent implements AllReitTransactions {

    private static final Logger logger = LoggerFactory.getLogger(AllReitTransactionsPersistent.class);

    @Autowired
    ReitTransactionRepository repository;

    @Override
    public ReitTransaction save(ReitTransaction reitTransaction) {
        ReitTransactionRow reitTransactionRow = ReitTransactionToRow.convert(reitTransaction);

        ReitTransactionRow savedReitTransactionRow = repository.save(reitTransactionRow);
        logger.info("Saved reit transaction {}", savedReitTransactionRow);

        return RowToReitTransaction.convert(savedReitTransactionRow);
    }
}
