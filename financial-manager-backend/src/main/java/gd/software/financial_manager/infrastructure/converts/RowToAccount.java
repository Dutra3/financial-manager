package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Account;
import gd.software.financial_manager.domain.model.AccountType;
import gd.software.financial_manager.infrastructure.persistence.relational.AccountRow;

public class RowToAccount {

    public static Account convert(AccountRow row) {
        return new Account(row.getId(), row.getName(), AccountType.valueOf(row.getAccountType().name()),
                row.getInitialBalance(), row.getUser() != null ? row.getUser().getId() : null);
    }
}
