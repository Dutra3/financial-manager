package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.domain.model.Installment;
import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.domain.usecase.collections.AllTransactions;
import gd.software.financial_manager.infrastructure.converts.RowToTransaction;
import gd.software.financial_manager.infrastructure.converts.TransactionToRow;
import gd.software.financial_manager.infrastructure.persistence.relational.TransactionRow;
import gd.software.financial_manager.infrastructure.persistence.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AllTransactionsPersistent implements AllTransactions {

    private static final Logger logger = LoggerFactory.getLogger(AllTransactionsPersistent.class);

    @Autowired
    TransactionRepository repository;

    //TODO: Create a logic to save user inside transaction!
    @Override
    public Transaction save(Transaction transaction) {
        TransactionRow row = TransactionToRow.convert(transaction);

        logger.info("Save transaction {}", row.getName());
        TransactionRow savedTransaction = repository.save(row);

        return RowToTransaction.convert(savedTransaction);
    }

    @Override
    public List<Transaction> allBy(UUID id) {
        logger.info("Find all Transactions by User id {}.", id);
        return repository.findByUserId(id).stream().map(RowToTransaction::convert).toList();
    }

    @Override
    public void remove(UUID id) {
        logger.info("Remove Transaction by id {}.", id);
        repository.deleteById(id);
    }

    @Override
    public List<Installment> byUserIdAndType(UUID id, CategoryType type) {
        return repository.findByUserIdAndType(id, type.name()).stream()
                .map(Installment::new)
                .toList();
    }
}
