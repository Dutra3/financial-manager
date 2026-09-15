package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Budget;
import gd.software.financial_manager.domain.usecase.collections.AllBudgets;
import gd.software.financial_manager.infrastructure.converts.BudgetToRow;
import gd.software.financial_manager.infrastructure.converts.RowToBudget;
import gd.software.financial_manager.infrastructure.persistence.relational.BudgetRow;
import gd.software.financial_manager.infrastructure.persistence.repository.BudgetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AllBudgetsPersistent implements AllBudgets {

    private static final Logger logger = LoggerFactory.getLogger(AllBudgetsPersistent.class);

    private final BudgetRepository repository;

    public AllBudgetsPersistent(BudgetRepository repository) {
        this.repository = repository;
    }

    @Override
    public Budget save(Budget budget) {
        BudgetRow row = BudgetToRow.convert(budget);

        if (row.getId() != null && repository.existsById(row.getId())) {
            BudgetRow existing = repository.findById(row.getId()).orElseThrow();
            row.setUser(existing.getUser());
        }

        logger.info("Save budget for category {} month {}/{}", budget.categoryId(), budget.month(), budget.year());
        BudgetRow savedBudget = repository.save(row);
        return RowToBudget.convert(savedBudget);
    }

    @Override
    public List<Budget> byUserId(UUID userId) {
        logger.info("Find budgets by User id {}.", userId);
        return repository.findByUserId(userId).stream()
                .map(RowToBudget::convert)
                .toList();
    }

    @Override
    public List<Budget> byUserIdAndMonthAndYear(UUID userId, Integer month, Integer year) {
        logger.info("Find budgets by User id {} for {}/{}.", userId, month, year);
        return repository.findByUserIdAndMonthAndYear(userId, month, year).stream()
                .map(RowToBudget::convert)
                .toList();
    }

    @Override
    public void remove(UUID id) {
        logger.info("Remove budget by id {}.", id);
        repository.deleteById(id);
    }
}
