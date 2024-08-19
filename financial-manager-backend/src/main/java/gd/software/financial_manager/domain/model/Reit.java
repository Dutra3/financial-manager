package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Reit {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal quantity;
    private BigDecimal averagePrice;
    private BigDecimal priceToBookRatio;
    private BigDecimal dividend;
    private BigDecimal dividendYield;

    public Reit(UUID id, String name, String description, BigDecimal quantity, BigDecimal averagePrice, BigDecimal priceToBookRatio, BigDecimal dividend, BigDecimal dividendYield) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.averagePrice = averagePrice;
        this.priceToBookRatio = priceToBookRatio;
        this.dividend = dividend;
        this.dividendYield = dividendYield;
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public BigDecimal quantity() {
        return quantity;
    }

    public BigDecimal averagePrice() {
        return averagePrice;
    }

    public BigDecimal priceToBookRatio() {
        return priceToBookRatio;
    }

    public BigDecimal dividend() {
        return dividend;
    }

    public BigDecimal dividendYield() {
        return dividendYield;
    }
}
