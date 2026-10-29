package gd.software.financial_manager.domain.usecase.investment;

import gd.software.financial_manager.domain.model.AssetTransaction;
import gd.software.financial_manager.domain.model.AssetTransactionType;
import gd.software.financial_manager.domain.model.PortfolioPosition;
import gd.software.financial_manager.domain.usecase.collections.AllAssetTransactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FetchPortfolio {

    private static final Logger logger = LoggerFactory.getLogger(FetchPortfolio.class);

    private final AllAssetTransactions allAssetTransactions;

    public FetchPortfolio(AllAssetTransactions allAssetTransactions) {
        this.allAssetTransactions = allAssetTransactions;
    }

    public List<PortfolioPosition> byUser(UUID userId) {
        logger.info("Building portfolio for user {}.", userId);
        List<AssetTransaction> transactions = allAssetTransactions.allBy(userId);

        return transactions.stream()
                .collect(Collectors.groupingBy(t -> t.asset().id()))
                .values().stream()
                .map(this::toPosition)
                .filter(p -> p.quantity().signum() > 0)
                .toList();
    }

    private PortfolioPosition toPosition(List<AssetTransaction> group) {
        BigDecimal buyQty = BigDecimal.ZERO;
        BigDecimal buyCost = BigDecimal.ZERO;
        BigDecimal quantity = BigDecimal.ZERO;
        BigDecimal invested = BigDecimal.ZERO;
        BigDecimal dividends = BigDecimal.ZERO;

        for (AssetTransaction t : group) {
            BigDecimal qty = t.quantity() != null ? t.quantity() : BigDecimal.ZERO;
            BigDecimal price = t.price() != null ? t.price() : BigDecimal.ZERO;
            if (t.type() == AssetTransactionType.BUY) {
                quantity = quantity.add(qty);
                buyQty = buyQty.add(qty);
                buyCost = buyCost.add(qty.multiply(price));
                invested = invested.add(qty.multiply(price));
            } else if (t.type() == AssetTransactionType.SELL) {
                quantity = quantity.subtract(qty);
                invested = invested.subtract(qty.multiply(price));
            } else if (t.type() == AssetTransactionType.DIVIDEND) {
                dividends = dividends.add(qty.multiply(price));
            }
        }

        BigDecimal averagePrice = buyQty.signum() > 0
                ? buyCost.divide(buyQty, 4, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        return new PortfolioPosition(group.get(0).asset(), quantity, averagePrice, invested, dividends);
    }
}
