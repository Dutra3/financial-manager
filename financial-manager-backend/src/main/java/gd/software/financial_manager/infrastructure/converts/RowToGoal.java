package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Goal;
import gd.software.financial_manager.infrastructure.persistence.relational.GoalRow;

public class RowToGoal {

    public static Goal convert(GoalRow row) {
        return new Goal(row.getId(), row.getName(), row.getDescription(), row.getTargetDate(),
                row.getTargetAmount(), row.getIsAchieved());
    }
}
