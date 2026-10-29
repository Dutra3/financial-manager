package gd.software.financial_manager.domain.usecase.collections;

import gd.software.financial_manager.domain.model.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AllAccounts {

    Account save(Account account);
    List<Account> byUserId(UUID userId);
    Optional<Account> byId(UUID id);
    void remove(UUID id);
}
