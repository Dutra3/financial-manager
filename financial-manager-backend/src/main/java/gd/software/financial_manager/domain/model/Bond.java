package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Bond {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal averagePrice;

    public Bond(UUID id, String name, String description, BigDecimal quantity, BigDecimal price, BigDecimal averagePrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.averagePrice = averagePrice;
    }

    public Bond(UUID id, String name, String description, BigDecimal quantity, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
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

    public BigDecimal price() {
        return price;
    }

    public BigDecimal averagePrice() {
        return averagePrice;
    }
}
