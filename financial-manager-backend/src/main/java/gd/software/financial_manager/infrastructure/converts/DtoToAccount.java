package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Account;
import gd.software.financial_manager.domain.model.AccountType;
import gd.software.financial_manager.infrastructure.dtos.AccountDTO;

public class DtoToAccount {

    public static Account convert(AccountDTO dto) {
        return new Account(dto.id(), dto.name(), AccountType.valueOf(dto.type()), dto.initialBalance(), null);
    }
}
