package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Goal;
import gd.software.financial_manager.infrastructure.dtos.GoalDTO;

public class DtoToGoal {

    public static Goal convert(GoalDTO dto) {
        return new Goal(dto.id(), dto.name(), dto.description(), dto.targetDate(),
                dto.targetAmount(), dto.isAchieved() != null ? dto.isAchieved() : false);
    }
}
