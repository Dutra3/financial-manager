package gd.software.financial_manager.domain.usecase.budget;

import gd.software.financial_manager.domain.model.Budget;
import gd.software.financial_manager.domain.usecase.collections.AllBudgets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreateBudget {

    private static final Logger logger = LoggerFactory.getLogger(CreateBudget.class);

    private final AllBudgets allBudgets;

    public CreateBudget(AllBudgets allBudgets) {
        this.allBudgets = allBudgets;
    }

    public Budget use(Budget budget) {
        logger.info("Creating budget for category {}.", budget.categoryId());
        return allBudgets.save(budget);
    }
}
