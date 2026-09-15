package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Goal;
import gd.software.financial_manager.infrastructure.persistence.relational.GoalRow;

public class GoalToRow {

    public static GoalRow convert(Goal goal) {
        return GoalRow.builder()
                .id(goal.id())
                .name(goal.name())
                .description(goal.description())
                .targetDate(goal.targetDate())
                .targetAmount(goal.targetAmount())
                .isAchieved(goal.isAchieved())
                .build();
    }
}
