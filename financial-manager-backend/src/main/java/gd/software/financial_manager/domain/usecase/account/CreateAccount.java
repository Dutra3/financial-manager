package gd.software.financial_manager.domain.usecase.account;

import gd.software.financial_manager.domain.model.Account;
import gd.software.financial_manager.domain.usecase.collections.AllAccounts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreateAccount {

    private static final Logger logger = LoggerFactory.getLogger(CreateAccount.class);

    private final AllAccounts allAccounts;

    public CreateAccount(AllAccounts allAccounts) {
        this.allAccounts = allAccounts;
    }

    public Account use(Account account) {
        logger.info("Creating account {} for user {}.", account.name(), account.userId());
        return allAccounts.save(account);
    }
}
