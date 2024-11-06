package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ReitTransaction {

    private UUID id;
    private Reit reit;
    private BigDecimal quantity;
    private BigDecimal price;
    private LocalDate transactionDate;

    public ReitTransaction(UUID id, Reit reit, BigDecimal quantity, BigDecimal price, LocalDate transactionDate) {
        this.id = id;
        this.reit = reit;
        this.quantity = quantity;
        this.price = price;
        this.transactionDate = transactionDate;
    }

    public UUID id() {
        return id;
    }

    public Reit reit() {
        return reit;
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
