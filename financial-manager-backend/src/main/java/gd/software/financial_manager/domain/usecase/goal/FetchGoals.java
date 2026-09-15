package gd.software.financial_manager.domain.usecase.goal;

import gd.software.financial_manager.domain.model.Goal;
import gd.software.financial_manager.domain.usecase.collections.AllGoals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FetchGoals {

    private static final Logger logger = LoggerFactory.getLogger(FetchGoals.class);

    private final AllGoals allGoals;

    public FetchGoals(AllGoals allGoals) {
        this.allGoals = allGoals;
    }

    public List<Goal> byUserId(UUID userId) {
        logger.info("Fetching goals for user {}.", userId);
        return allGoals.byUserId(userId);
    }
}
