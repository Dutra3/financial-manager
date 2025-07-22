package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Profile;
import gd.software.financial_manager.infrastructure.dtos.ProfileDTO;

public class DtoToProfile {

    public static Profile convert(ProfileDTO dto) {
        return new Profile(dto.id(), dto.name(), dto.profession(), dto.salary(), dto.payDay(), dto.initialBalance(), dto.financialGoal());
    }
}
