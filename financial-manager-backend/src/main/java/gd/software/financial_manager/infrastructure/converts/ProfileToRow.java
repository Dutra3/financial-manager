package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Profile;
import gd.software.financial_manager.infrastructure.persistence.relational.ProfileRow;
import gd.software.financial_manager.infrastructure.persistence.relational.UserRow;

public class ProfileToRow {

    public static ProfileRow convert(Profile profile) {
        ProfileRow.ProfileRowBuilder builder = ProfileRow.builder()
                .id(profile.id())
                .name(profile.name())
                .profession(profile.profession())
                .netSalary(profile.netSalary())
                .payday(profile.payday())
                .initialBalance(profile.initialBalance())
                .financialGoal(profile.financialGoal());

        if (profile.userId() != null) {
            builder.user(UserRow.builder().id(profile.userId()).build());
        }

        return builder.build();
    }
}
