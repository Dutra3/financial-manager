package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class BondTransaction {

    private UUID id;
    private Bond bond;
    private BigDecimal quantity;
    private BigDecimal price;
    private LocalDate transactionDate;

    public BondTransaction(UUID id, Bond bond, BigDecimal quantity, BigDecimal price, LocalDate transactionDate) {
        this.id = id;
        this.bond = bond;
        this.quantity = quantity;
        this.price = price;
        this.transactionDate = transactionDate;
    }

    public UUID id() {
        return id;
    }

    public Bond bond() {
        return bond;
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
}
