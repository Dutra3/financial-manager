package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.StockTransaction;
import gd.software.financial_manager.domain.usecase.collections.AllStockTransactions;
import gd.software.financial_manager.infrastructure.converts.RowToStockTransaction;
import gd.software.financial_manager.infrastructure.converts.StockTransactionToRow;
import gd.software.financial_manager.infrastructure.persistence.relational.StockTransactionRow;
import gd.software.financial_manager.infrastructure.persistence.repository.StockTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AllStockTransactionsPersistent implements AllStockTransactions {

    private static final Logger logger = LoggerFactory.getLogger(AllStockTransactionsPersistent.class);

    @Autowired
    StockTransactionRepository repository;

    @Override
    public StockTransaction save(StockTransaction stockTransaction) {
        StockTransactionRow stockTransactionRow = StockTransactionToRow.convert(stockTransaction);

        StockTransactionRow savedStockTransactionRow = repository.save(stockTransactionRow);
        logger.info("Saved stock transaction {}", savedStockTransactionRow);

        return RowToStockTransaction.convert(savedStockTransactionRow);
    }
}
