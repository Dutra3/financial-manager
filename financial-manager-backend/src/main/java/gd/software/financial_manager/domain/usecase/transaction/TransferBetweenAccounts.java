package gd.software.financial_manager.domain.usecase.transaction;

import gd.software.financial_manager.domain.model.Account;
import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.domain.usecase.collections.AllAccounts;
import gd.software.financial_manager.domain.usecase.collections.AllCategories;
import gd.software.financial_manager.domain.usecase.collections.AllTransactions;
import gd.software.financial_manager.infrastructure.utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransferBetweenAccounts {

    private static final Logger logger = LoggerFactory.getLogger(TransferBetweenAccounts.class);
    private static final String TRANSFER_CATEGORY = "Transfer";

    private final AllAccounts allAccounts;
    private final AllCategories allCategories;
    private final AllTransactions allTransactions;

    public TransferBetweenAccounts(AllAccounts allAccounts, AllCategories allCategories, AllTransactions allTransactions) {
        this.allAccounts = allAccounts;
        this.allCategories = allCategories;
        this.allTransactions = allTransactions;
    }

    public void use(UUID fromAccountId, UUID toAccountId, BigDecimal amount, LocalDate date, String description) {
        if (fromAccountId.equals(toAccountId)) {
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        }
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive.");
        }

        Account from = allAccounts.byId(fromAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Source account not found"));
        Account to = allAccounts.byId(toAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Target account not found"));

        SecurityUtils.requireOwner(from.userId());
        SecurityUtils.requireOwner(to.userId());
        if (from.userId() != null && to.userId() != null && !from.userId().equals(to.userId())) {
            throw new IllegalArgumentException("Transfers are only allowed between accounts of the same user.");
        }

        Category debitCategory = transferCategory(CategoryType.DEBIT);
        Category creditCategory = transferCategory(CategoryType.CREDIT);
        String label = "Transfer " + from.name() + " -> " + to.name();

        allTransactions.save(new Transaction(null, label, description, amount, date,
                debitCategory, from.userId(), fromAccountId));
        allTransactions.save(new Transaction(null, label, description, amount, date,
                creditCategory, to.userId(), toAccountId));

        logger.info("Transfer of {} from account {} to {}.", amount, fromAccountId, toAccountId);
    }

    private Category transferCategory(CategoryType type) {
        return allCategories.all().stream()
                .filter(c -> c.name().equals(TRANSFER_CATEGORY) && c.type() == type)
                .findFirst()
                .orElseGet(() -> allCategories.save(new Category(null, TRANSFER_CATEGORY, type)));
    }
}
