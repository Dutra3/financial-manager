package gd.software.financial_manager.domain.usecase.account;

import gd.software.financial_manager.domain.model.Account;
import gd.software.financial_manager.domain.usecase.collections.AllAccounts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FetchAccounts {

    private static final Logger logger = LoggerFactory.getLogger(FetchAccounts.class);

    private final AllAccounts allAccounts;

    public FetchAccounts(AllAccounts allAccounts) {
        this.allAccounts = allAccounts;
    }

    public List<Account> byUserId(UUID userId) {
        logger.info("Fetching accounts for user {}.", userId);
        return allAccounts.byUserId(userId);
    }
}
