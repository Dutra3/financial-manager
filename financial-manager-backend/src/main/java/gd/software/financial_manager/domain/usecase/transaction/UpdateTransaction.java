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
public class UpdateTransaction {

    private static final Logger logger = LoggerFactory.getLogger(UpdateTransaction.class);

    private final AllTransactions allTransactions;
    private final AllCategories allCategories;

    public UpdateTransaction(AllTransactions allTransactions, AllCategories allCategories) {
        this.allTransactions = allTransactions;
        this.allCategories = allCategories;
    }

    public Transaction use(Transaction transaction) {
        logger.info("Update transaction {}", transaction.id());

        Transaction existing = allTransactions.byId(transaction.id())
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with id " + transaction.id()));

        Optional<Category> category = allCategories.by(transaction.category().id());
        category.ifPresentOrElse(
            existing::setCategory, () -> {
                throw new EntityNotFoundException("Category not found for this transaction");
            });

        existing.setName(transaction.name());
        existing.setDescription(transaction.description());
        existing.setAmount(transaction.amount());
        existing.setPaymentDate(transaction.paymentDate());

        return allTransactions.save(existing);
    }
}
