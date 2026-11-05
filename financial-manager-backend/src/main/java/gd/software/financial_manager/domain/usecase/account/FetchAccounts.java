package gd.software.financial_manager.domain.usecase.account;

import gd.software.financial_manager.domain.model.Account;
import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.domain.usecase.collections.AllAccounts;
import gd.software.financial_manager.domain.usecase.collections.AllTransactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class FetchAccounts {

    private static final Logger logger = LoggerFactory.getLogger(FetchAccounts.class);

    private final AllAccounts allAccounts;
    private final AllTransactions allTransactions;

    public FetchAccounts(AllAccounts allAccounts, AllTransactions allTransactions) {
        this.allAccounts = allAccounts;
        this.allTransactions = allTransactions;
    }

    public List<Account> byUserId(UUID userId) {
        logger.info("Fetching accounts for user {}.", userId);
        return allAccounts.byUserId(userId);
    }

    public Map<UUID, BigDecimal> balanceDeltaByAccount(UUID userId) {
        Map<UUID, BigDecimal> deltas = new HashMap<>();
        for (Transaction t : allTransactions.allBy(userId)) {
            if (t.accountId() == null || t.category() == null) {
                continue;
            }
            BigDecimal delta = t.category().type() == CategoryType.CREDIT ? t.amount() : t.amount().negate();
            deltas.merge(t.accountId(), delta, BigDecimal::add);
        }
        return deltas;
    }
}
