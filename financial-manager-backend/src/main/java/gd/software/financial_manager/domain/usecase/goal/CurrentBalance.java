package gd.software.financial_manager.domain.usecase.goal;

import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.domain.model.Profile;
import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.domain.usecase.collections.AllProfiles;
import gd.software.financial_manager.domain.usecase.collections.AllTransactions;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class CurrentBalance {

    private static final Logger logger = LoggerFactory.getLogger(CurrentBalance.class);

    private final AllProfiles allProfiles;
    private final AllTransactions allTransactions;

    public CurrentBalance(AllProfiles allProfiles, AllTransactions allTransactions) {
        this.allProfiles = allProfiles;
        this.allTransactions = allTransactions;
    }

    public BigDecimal forUser(UUID userId) {
        logger.info("Calculating current balance for user {}.", userId);

        Profile profile = allProfiles.byId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found for user " + userId));

        List<Transaction> transactions = allTransactions.allBy(userId);

        BigDecimal credits = transactions.stream()
                .filter(t -> t.category() != null && t.category().type() == CategoryType.CREDIT)
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal debits = transactions.stream()
                .filter(t -> t.category() != null && t.category().type() == CategoryType.DEBIT)
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return profile.initialBalance().add(credits).subtract(debits);
    }
}
