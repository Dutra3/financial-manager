package gd.software.financial_manager.domain.usecase.budget;

import gd.software.financial_manager.domain.model.Budget;
import gd.software.financial_manager.domain.usecase.collections.AllBudgets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FetchBudgets {

    private static final Logger logger = LoggerFactory.getLogger(FetchBudgets.class);

    private final AllBudgets allBudgets;

    public FetchBudgets(AllBudgets allBudgets) {
        this.allBudgets = allBudgets;
    }

    public List<Budget> byUserId(UUID userId) {
        logger.info("Fetching budgets for user {}.", userId);
        return allBudgets.byUserId(userId);
    }

    public List<Budget> byUserIdAndMonthAndYear(UUID userId, Integer month, Integer year) {
        logger.info("Fetching budgets for user {} on {}/{}.", userId, month, year);
        return allBudgets.byUserIdAndMonthAndYear(userId, month, year);
    }
}
