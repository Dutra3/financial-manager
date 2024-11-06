package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Reit {

    private UUID id;
    private String name;
    private String ticker;
    private String description;
    private String type;
    private String industrySegment;
    private BigDecimal price;

    public Reit(UUID id) {
        this.id = id;
    }

    public Reit(UUID id, String name, String ticker, String description, String type, String industrySegment, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.ticker = ticker;
        this.description = description;
        this.type = type;
        this.industrySegment = industrySegment;
        this.price = price;
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

    public String type() {
        return type;
    }

    public String industrySegment() {
        return industrySegment;
    }

    public BigDecimal price() {
        return price;
    }
}
