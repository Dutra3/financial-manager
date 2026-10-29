package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Account;
import gd.software.financial_manager.infrastructure.dtos.AccountDTO;

public class AccountToDTO {

    public static AccountDTO convert(Account account) {
        return new AccountDTO(account.id(), account.name(), account.type().name(), account.initialBalance());
    }
}
