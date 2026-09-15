package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Goal;
import gd.software.financial_manager.domain.usecase.collections.AllGoals;
import gd.software.financial_manager.infrastructure.converts.GoalToRow;
import gd.software.financial_manager.infrastructure.converts.RowToGoal;
import gd.software.financial_manager.infrastructure.persistence.relational.GoalRow;
import gd.software.financial_manager.infrastructure.persistence.repository.GoalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AllGoalsPersistent implements AllGoals {

    private static final Logger logger = LoggerFactory.getLogger(AllGoalsPersistent.class);

    private final GoalRepository repository;

    public AllGoalsPersistent(GoalRepository repository) {
        this.repository = repository;
    }

    @Override
    public Goal save(Goal goal) {
        GoalRow row = GoalToRow.convert(goal);

        if (row.getId() != null && repository.existsById(row.getId())) {
            GoalRow existing = repository.findById(row.getId()).orElseThrow();
            row.setUser(existing.getUser());
        }

        logger.info("Save goal {}", row.getName());
        GoalRow savedGoal = repository.save(row);
        return RowToGoal.convert(savedGoal);
    }

    @Override
    public List<Goal> byUserId(UUID userId) {
        logger.info("Find goals by User id {}.", userId);
        return repository.findByUserId(userId).stream()
                .map(RowToGoal::convert)
                .toList();
    }

    @Override
    public Optional<Goal> byId(UUID id) {
        logger.info("Find goal by id {}.", id);
        return repository.findById(id).map(RowToGoal::convert);
    }

    @Override
    public void remove(UUID id) {
        logger.info("Remove goal by id {}.", id);
        repository.deleteById(id);
    }
}
