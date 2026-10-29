package gd.software.financial_manager.domain.usecase.account;

import gd.software.financial_manager.domain.usecase.collections.AllAccounts;
import gd.software.financial_manager.infrastructure.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteAccount {

    private static final Logger logger = LoggerFactory.getLogger(DeleteAccount.class);

    private final AllAccounts allAccounts;

    public DeleteAccount(AllAccounts allAccounts) {
        this.allAccounts = allAccounts;
    }

    public void use(UUID id) {
        logger.info("Deleting account with id {}.", id);
        allAccounts.byId(id).ifPresent(account -> SecurityUtils.requireOwner(account.userId()));
        allAccounts.remove(id);
    }
}
