package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Budget {

    private UUID id;
    private UUID categoryId;
    private BigDecimal amount;
    private Integer month;
    private Integer year;
    private UUID userId;

    public Budget(UUID id, UUID categoryId, BigDecimal amount, Integer month, Integer year) {
        this(id, categoryId, amount, month, year, null);
    }

    public Budget(UUID id, UUID categoryId, BigDecimal amount, Integer month, Integer year, UUID userId) {
        this.id = id;
        this.categoryId = categoryId;
        this.amount = amount;
        this.month = month;
        this.year = year;
        this.userId = userId;
    }

    public UUID id() {
        return id;
    }

    public UUID categoryId() {
        return categoryId;
    }

    public BigDecimal amount() {
        return amount;
    }

    public Integer month() {
        return month;
    }

    public Integer year() {
        return year;
    }

    public UUID userId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
