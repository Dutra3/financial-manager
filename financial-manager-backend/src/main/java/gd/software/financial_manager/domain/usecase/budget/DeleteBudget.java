package gd.software.financial_manager.domain.usecase.budget;

import gd.software.financial_manager.domain.usecase.collections.AllBudgets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteBudget {

    private static final Logger logger = LoggerFactory.getLogger(DeleteBudget.class);

    private final AllBudgets allBudgets;

    public DeleteBudget(AllBudgets allBudgets) {
        this.allBudgets = allBudgets;
    }

    public void use(UUID id) {
        logger.info("Deleting budget with id {}.", id);
        allBudgets.remove(id);
    }
}
