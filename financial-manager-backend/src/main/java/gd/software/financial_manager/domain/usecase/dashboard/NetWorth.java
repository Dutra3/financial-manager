package gd.software.financial_manager.domain.usecase.dashboard;

import gd.software.financial_manager.domain.model.PortfolioPosition;
import gd.software.financial_manager.domain.usecase.account.FetchAccounts;
import gd.software.financial_manager.domain.usecase.investment.FetchPortfolio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Service
public class NetWorth {

    private static final Logger logger = LoggerFactory.getLogger(NetWorth.class);

    private final FetchAccounts fetchAccounts;
    private final FetchPortfolio fetchPortfolio;

    public NetWorth(FetchAccounts fetchAccounts, FetchPortfolio fetchPortfolio) {
        this.fetchAccounts = fetchAccounts;
        this.fetchPortfolio = fetchPortfolio;
    }

    public BigDecimal accountsBalance(UUID userId) {
        Map<UUID, BigDecimal> deltas = fetchAccounts.balanceDeltaByAccount(userId);
        return fetchAccounts.byUserId(userId).stream()
                .map(a -> a.initialBalance().add(deltas.getOrDefault(a.id(), BigDecimal.ZERO)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal investmentsValue(UUID userId) {
        return fetchPortfolio.byUser(userId).stream()
                .map(PortfolioPosition::currentValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal total(UUID userId) {
        logger.info("Calculating net worth for user {}.", userId);
        return accountsBalance(userId).add(investmentsValue(userId));
    }
}
