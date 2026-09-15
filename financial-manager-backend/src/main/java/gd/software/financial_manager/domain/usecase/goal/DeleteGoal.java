package gd.software.financial_manager.domain.usecase.goal;

import gd.software.financial_manager.domain.usecase.collections.AllGoals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteGoal {

    private static final Logger logger = LoggerFactory.getLogger(DeleteGoal.class);

    private final AllGoals allGoals;

    public DeleteGoal(AllGoals allGoals) {
        this.allGoals = allGoals;
    }

    public void use(UUID id) {
        logger.info("Deleting goal with id {}.", id);
        allGoals.remove(id);
    }
}
