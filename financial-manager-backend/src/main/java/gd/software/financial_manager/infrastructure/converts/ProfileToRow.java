package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Profile;
import gd.software.financial_manager.infrastructure.persistence.relational.ProfileRow;
import gd.software.financial_manager.infrastructure.persistence.relational.UserRow;

import java.util.UUID;

public class ProfileToRow {

    public static ProfileRow convert(Profile profile) {
        return ProfileRow.builder()
                .id(profile.id())
                .name(profile.name())
                .profession(profile.profession())
                .netSalary(profile.netSalary())
                .payday(profile.payday())
                .initialBalance(profile.initialBalance())
                .financialGoal(profile.financialGoal())
                .user(new UserRow(UUID.randomUUID(), "email@email.com", "password"))
                .build();
    }
}
