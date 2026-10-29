package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Account;
import gd.software.financial_manager.infrastructure.persistence.relational.AccountRow;
import gd.software.financial_manager.infrastructure.persistence.relational.AccountTypeRow;
import gd.software.financial_manager.infrastructure.persistence.relational.UserRow;

public class AccountToRow {

    public static AccountRow convert(Account account) {
        AccountRow.AccountRowBuilder builder = AccountRow.builder()
                .id(account.id())
                .name(account.name())
                .accountType(AccountTypeRow.valueOf(account.type().name()))
                .initialBalance(account.initialBalance());

        if (account.userId() != null) {
            builder.user(UserRow.builder().id(account.userId()).build());
        }

        return builder.build();
    }
}
