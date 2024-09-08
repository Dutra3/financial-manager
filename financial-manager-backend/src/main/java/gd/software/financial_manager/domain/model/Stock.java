package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Stock {

    private UUID id;
    private String name;
    private String ticker;
    private String description;
    private BigDecimal quantity;
    private BigDecimal price;
    private LocalDate transactionDate;

    public Stock(UUID id, String name, String ticker, String description, BigDecimal quantity, BigDecimal price,
                 LocalDate transactionDate) {
        this.id = id;
        this.name = name;
        this.ticker = ticker;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.transactionDate = transactionDate;
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String ticker() {
        return ticker;
    }

    public String description() {
        return description;
    }

    public BigDecimal quantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal price() {
        return price;
    }

    public LocalDate transactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}
