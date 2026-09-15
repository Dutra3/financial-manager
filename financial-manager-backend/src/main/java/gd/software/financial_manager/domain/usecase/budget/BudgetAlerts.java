package gd.software.financial_manager.domain.usecase.budget;

import gd.software.financial_manager.domain.model.Budget;
import gd.software.financial_manager.domain.model.BudgetAlert;
import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.domain.usecase.collections.AllBudgets;
import gd.software.financial_manager.domain.usecase.collections.AllCategories;
import gd.software.financial_manager.domain.usecase.collections.AllTransactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BudgetAlerts {

    private static final Logger logger = LoggerFactory.getLogger(BudgetAlerts.class);
    private static final BigDecimal WARNING_THRESHOLD = new BigDecimal("80");

    private final AllBudgets allBudgets;
    private final AllTransactions allTransactions;
    private final AllCategories allCategories;

    public BudgetAlerts(AllBudgets allBudgets, AllTransactions allTransactions, AllCategories allCategories) {
        this.allBudgets = allBudgets;
        this.allTransactions = allTransactions;
        this.allCategories = allCategories;
    }

    public List<BudgetAlert> forUserAndMonth(UUID userId, Integer month, Integer year) {
        logger.info("Generating budget alerts for user {} on {}/{}.", userId, month, year);

        List<Budget> budgets = allBudgets.byUserIdAndMonthAndYear(userId, month, year);
        List<Transaction> transactions = allTransactions.allBy(userId);

        Map<UUID, BigDecimal> spentByCategory = transactions.stream()
                .filter(t -> t.category() != null && t.category().type() == CategoryType.DEBIT)
                .filter(t -> t.paymentDate() != null
                        && t.paymentDate().getMonthValue() == month
                        && t.paymentDate().getYear() == year)
                .collect(Collectors.groupingBy(
                        t -> t.category().id(),
                        Collectors.reducing(BigDecimal.ZERO, Transaction::amount, BigDecimal::add)
                ));

        return budgets.stream()
                .map(budget -> {
                    Category category = allCategories.by(budget.categoryId()).orElse(null);
                    String categoryName = category != null ? category.name() : "Unknown";
                    BigDecimal spent = spentByCategory.getOrDefault(budget.categoryId(), BigDecimal.ZERO);
                    BigDecimal percentage = budget.amount().compareTo(BigDecimal.ZERO) > 0
                            ? spent.divide(budget.amount(), 2, RoundingMode.HALF_UP).multiply(new BigDecimal("100"))
                            : BigDecimal.ZERO;

                    BudgetAlert.AlertStatus status;
                    if (percentage.compareTo(new BigDecimal("100")) >= 0) {
                        status = BudgetAlert.AlertStatus.EXCEEDED;
                    } else if (percentage.compareTo(WARNING_THRESHOLD) >= 0) {
                        status = BudgetAlert.AlertStatus.WARNING;
                    } else {
                        status = BudgetAlert.AlertStatus.OK;
                    }

                    return new BudgetAlert(budget.categoryId(), categoryName, budget.amount(),
                            spent, percentage, status);
                })
                .toList();
    }
}
