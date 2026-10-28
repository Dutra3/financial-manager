package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class AssetTransaction {

    private UUID id;
    private Asset asset;
    private UUID userId;
    private AssetTransactionType type;
    private BigDecimal quantity;
    private BigDecimal price;
    private LocalDate transactionDate;

    public AssetTransaction(UUID id, Asset asset, UUID userId, AssetTransactionType type,
                            BigDecimal quantity, BigDecimal price, LocalDate transactionDate) {
        this.id = id;
        this.asset = asset;
        this.userId = userId;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.transactionDate = transactionDate;
    }

    public UUID id() {
        return id;
    }

    public Asset asset() {
        return asset;
    }

    public UUID userId() {
        return userId;
    }

    public AssetTransactionType type() {
        return type;
    }

    public BigDecimal quantity() {
        return quantity;
    }

    public BigDecimal price() {
        return price;
    }

    public LocalDate transactionDate() {
        return transactionDate;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
