package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class PortfolioPosition {

    private final Asset asset;
    private final BigDecimal quantity;
    private final BigDecimal averagePrice;
    private final BigDecimal totalInvested;
    private final BigDecimal dividends;

    public PortfolioPosition(Asset asset, BigDecimal quantity, BigDecimal averagePrice,
                             BigDecimal totalInvested, BigDecimal dividends) {
        this.asset = asset;
        this.quantity = quantity;
        this.averagePrice = averagePrice;
        this.totalInvested = totalInvested;
        this.dividends = dividends;
    }

    public UUID assetId() {
        return asset.id();
    }

    public Asset asset() {
        return asset;
    }

    public BigDecimal quantity() {
        return quantity;
    }

    public BigDecimal averagePrice() {
        return averagePrice;
    }

    public BigDecimal totalInvested() {
        return totalInvested;
    }

    public BigDecimal dividends() {
        return dividends;
    }

    public BigDecimal currentValue() {
        return asset.price() != null ? asset.price().multiply(quantity) : BigDecimal.ZERO;
    }

    public BigDecimal profitLoss() {
        return currentValue().subtract(quantity.multiply(averagePrice)).add(dividends);
    }

    public BigDecimal profitLossPercent() {
        BigDecimal cost = quantity.multiply(averagePrice);
        if (cost.signum() == 0) {
            return BigDecimal.ZERO;
        }
        return profitLoss().divide(cost, 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
    }
}
