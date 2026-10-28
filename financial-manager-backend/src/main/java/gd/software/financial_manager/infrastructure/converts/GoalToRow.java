package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Goal;
import gd.software.financial_manager.infrastructure.persistence.relational.GoalRow;
import gd.software.financial_manager.infrastructure.persistence.relational.UserRow;

public class GoalToRow {

    public static GoalRow convert(Goal goal) {
        GoalRow.GoalRowBuilder builder = GoalRow.builder()
                .id(goal.id())
                .name(goal.name())
                .description(goal.description())
                .targetDate(goal.targetDate())
                .targetAmount(goal.targetAmount())
                .isAchieved(goal.isAchieved());

        if (goal.userId() != null) {
            builder.user(UserRow.builder().id(goal.userId()).build());
        }

        return builder.build();
    }
}
