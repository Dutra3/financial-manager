package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Profile;
import gd.software.financial_manager.infrastructure.persistence.relational.ProfileRow;

public class RowToProfile {

    public static Profile convert(ProfileRow row) {
        return new Profile(row.getId(), row.getName(), row.getProfession(), row.getNetSalary(), row.getPayday(), row.getInitialBalance(),
                row.getFinancialGoal());
    }
}
