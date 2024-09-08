package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

public class Bond {

    private UUID id;
    private String name;
    private String ticker;
    private String description;
    private BigDecimal quantity;
    private BigDecimal price;
    private LocalDate transactionDate;
    private BigDecimal averagePrice;

    public Bond(UUID id, String name, String ticker, String description, BigDecimal quantity, BigDecimal price,
                LocalDate transactionDate, BigDecimal averagePrice) {
        this.id = id;
        this.name = name;
        this.ticker = ticker;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.transactionDate = transactionDate;
        this.averagePrice = averagePrice;
    }

    public Bond(UUID id, String name, String ticker, String description, BigDecimal quantity, BigDecimal price,
                LocalDate transactionDate) {
        this.id = id;
        this.name = name;
        this.ticker = ticker;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.transactionDate = transactionDate;
    }

    public void calculateAveragePrice(BigDecimal quantity, BigDecimal price) {
        this.quantity = this.quantity.add(quantity);
        var totalAmount = this.quantity.multiply(this.averagePrice);
        totalAmount = totalAmount.add(price);

        this.averagePrice = totalAmount.divide(this.quantity, 4, RoundingMode.HALF_EVEN);
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

    public BigDecimal averagePrice() {
        return averagePrice;
    }
}
