package gd.software.financial_manager.domain.usecase.collections;

import gd.software.financial_manager.domain.model.Goal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AllGoals {

    Goal save(Goal goal);

    List<Goal> byUserId(UUID userId);

    Optional<Goal> byId(UUID id);

    void remove(UUID id);
}
