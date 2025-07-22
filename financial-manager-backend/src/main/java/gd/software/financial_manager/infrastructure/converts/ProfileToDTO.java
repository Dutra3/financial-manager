package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Profile;
import gd.software.financial_manager.infrastructure.dtos.ProfileDTO;

public class ProfileToDTO {

    public static ProfileDTO convert(Profile profile) {
        return new ProfileDTO(profile.id(), profile.name(), profile.profession(), profile.netSalary(), profile.payDay(),
                profile.initialBalance(), profile.financialGoal());
    }
}
