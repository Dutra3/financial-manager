package gd.software.financial_manager.domain.usecase.transaction;

import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.domain.usecase.collections.AllCategories;
import gd.software.financial_manager.domain.usecase.collections.AllTransactions;
import gd.software.financial_manager.infrastructure.utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CreateRecurringTransactions {

    private static final Logger logger = LoggerFactory.getLogger(CreateRecurringTransactions.class);

    private final AllTransactions allTransactions;
    private final AllCategories allCategories;

    public CreateRecurringTransactions(AllTransactions allTransactions, AllCategories allCategories) {
        this.allTransactions = allTransactions;
        this.allCategories = allCategories;
    }

    public List<Transaction> use(String name, String description, BigDecimal amount, LocalDate firstPaymentDate,
                                 UUID categoryId, UUID accountId, UUID userId, int occurrences, String frequency) {
        Category category = allCategories.by(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id " + categoryId));

        UUID owner = SecurityUtils.resolveUserId(userId);
        List<Transaction> created = new ArrayList<>();

        for (int i = 0; i < occurrences; i++) {
            LocalDate date = switch (frequency) {
                case "WEEKLY" -> firstPaymentDate.plusWeeks(i);
                case "YEARLY" -> firstPaymentDate.plusYears(i);
                default -> firstPaymentDate.plusMonths(i);
            };
            created.add(allTransactions.save(new Transaction(null, name, description, amount, date,
                    category, owner, accountId)));
        }

        logger.info("Created {} recurring transactions {} for user {}.", occurrences, name, owner);
        return created;
    }
}
