package gd.software.financial_manager.domain.usecase.investment;

import gd.software.financial_manager.domain.model.AssetTransaction;
import gd.software.financial_manager.domain.model.AssetTransactionType;
import gd.software.financial_manager.domain.usecase.collections.AllAssetTransactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

@Service
public class FetchPortfolioHistory {

    private static final Logger logger = LoggerFactory.getLogger(FetchPortfolioHistory.class);

    private final AllAssetTransactions allAssetTransactions;

    public FetchPortfolioHistory(AllAssetTransactions allAssetTransactions) {
        this.allAssetTransactions = allAssetTransactions;
    }

    public TreeMap<YearMonth, BigDecimal> byUser(UUID userId) {
        logger.info("Building portfolio history for user {}.", userId);
        List<AssetTransaction> transactions = allAssetTransactions.allBy(userId);

        TreeMap<YearMonth, BigDecimal> history = new TreeMap<>();
        BigDecimal invested = BigDecimal.ZERO;

        List<AssetTransaction> sorted = transactions.stream()
                .filter(t -> t.transactionDate() != null)
                .sorted((a, b) -> a.transactionDate().compareTo(b.transactionDate()))
                .toList();

        for (AssetTransaction t : sorted) {
            BigDecimal qty = t.quantity() != null ? t.quantity() : BigDecimal.ZERO;
            BigDecimal price = t.price() != null ? t.price() : BigDecimal.ZERO;
            BigDecimal amount = qty.multiply(price);

            if (t.type() == AssetTransactionType.BUY) {
                invested = invested.add(amount);
            } else if (t.type() == AssetTransactionType.SELL) {
                invested = invested.subtract(amount);
            } else if (t.type() == AssetTransactionType.DIVIDEND) {
                invested = invested.add(amount);
            }

            history.put(YearMonth.from(t.transactionDate()), invested);
        }

        return history;
    }
}
