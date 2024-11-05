package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class StockTransaction {

    private UUID id;
    private Stock stock;
    private BigDecimal quantity;
    private BigDecimal price;
    private LocalDate transactionDate;

    public StockTransaction(UUID id, Stock stock, BigDecimal quantity, BigDecimal price, LocalDate transactionDate) {
        this.id = id;
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
        this.transactionDate = transactionDate;
    }

    public UUID id() {
        return id;
    }

    public Stock stock() {
        return stock;
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
