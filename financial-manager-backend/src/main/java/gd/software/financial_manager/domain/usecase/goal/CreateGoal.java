package gd.software.financial_manager.domain.usecase.goal;

import gd.software.financial_manager.domain.model.Goal;
import gd.software.financial_manager.domain.usecase.collections.AllGoals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreateGoal {

    private static final Logger logger = LoggerFactory.getLogger(CreateGoal.class);

    private final AllGoals allGoals;

    public CreateGoal(AllGoals allGoals) {
        this.allGoals = allGoals;
    }

    public Goal use(Goal goal) {
        logger.info("Creating goal with name {}.", goal.name());
        return allGoals.save(goal);
    }
}
