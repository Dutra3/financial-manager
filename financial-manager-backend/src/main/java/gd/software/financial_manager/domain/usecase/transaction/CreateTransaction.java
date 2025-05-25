package gd.software.financial_manager.domain.usecase.transaction;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.domain.usecase.collections.AllCategories;
import gd.software.financial_manager.domain.usecase.collections.AllTransactions;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreateTransaction {

    private static final Logger logger = LoggerFactory.getLogger(CreateTransaction.class);

    private final AllTransactions allTransactions;
    private final AllCategories allCategories;

    public CreateTransaction(AllTransactions allTransactions, AllCategories allCategories) {
        this.allTransactions = allTransactions;
        this.allCategories = allCategories;
    }

    public Transaction use(Transaction transaction) {
        logger.info("Create transaction {}", transaction.name());
        Optional<Category> category = allCategories.by(transaction.category().id());
        category.ifPresentOrElse(
            transaction::setCategory, () -> {
                throw new EntityNotFoundException("can not found category for this transaction");
            });

        return allTransactions.save(transaction);
    }
}
