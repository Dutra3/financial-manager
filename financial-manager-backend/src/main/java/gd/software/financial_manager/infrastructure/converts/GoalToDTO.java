package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Goal;
import gd.software.financial_manager.infrastructure.dtos.GoalDTO;

public class GoalToDTO {

    public static GoalDTO convert(Goal goal) {
        return new GoalDTO(goal.id(), goal.name(), goal.description(), goal.targetDate(),
                goal.targetAmount(), goal.isAchieved());
    }
}
