package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Account;
import gd.software.financial_manager.domain.usecase.collections.AllAccounts;
import gd.software.financial_manager.infrastructure.converts.AccountToRow;
import gd.software.financial_manager.infrastructure.converts.RowToAccount;
import gd.software.financial_manager.infrastructure.persistence.relational.AccountRow;
import gd.software.financial_manager.infrastructure.persistence.repository.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AllAccountsPersistent implements AllAccounts {

    private final AccountRepository repository;

    public AllAccountsPersistent(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Account save(Account account) {
        AccountRow row = AccountToRow.convert(account);

        if (row.getId() != null && repository.existsById(row.getId())) {
            AccountRow existing = repository.findById(row.getId()).orElseThrow();
            row.setUser(existing.getUser());
        }

        return RowToAccount.convert(repository.save(row));
    }

    @Override
    public List<Account> byUserId(UUID userId) {
        return repository.findByUserId(userId).stream().map(RowToAccount::convert).toList();
    }

    @Override
    public Optional<Account> byId(UUID id) {
        return repository.findById(id).map(RowToAccount::convert);
    }

    @Override
    public void remove(UUID id) {
        repository.deleteById(id);
    }
}
