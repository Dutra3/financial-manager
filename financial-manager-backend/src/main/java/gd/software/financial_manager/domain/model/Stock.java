package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Stock {

    private UUID id;
    private String name;
    private String ticker;
    private String description;
    private String type;
    private String industrySegment;
    private BigDecimal tagAlong;
    private BigDecimal price;
    private BigDecimal peRatio;
    private BigDecimal dividendYield;
    private BigDecimal pbRatio;
    private BigDecimal lastDividend;
    private Boolean isBesst;
    private Boolean isNewMarket;

    public Stock(UUID id) {
        this.id = id;
    }

    public Stock(UUID id, String name, String ticker, String description, String type, String industrySegment,
                 BigDecimal tagAlong, BigDecimal price, BigDecimal peRatio, BigDecimal dividendYield,
                 BigDecimal pbRatio, BigDecimal lastDividend, Boolean isBesst, Boolean isNewMarket) {
        this.id = id;
        this.name = name;
        this.ticker = ticker;
        this.description = description;
        this.type = type;
        this.industrySegment = industrySegment;
        this.tagAlong = tagAlong;
        this.price = price;
        this.peRatio = peRatio;
        this.dividendYield = dividendYield;
        this.pbRatio = pbRatio;
        this.lastDividend = lastDividend;
        this.isBesst = isBesst;
        this.isNewMarket = isNewMarket;
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

    public BigDecimal tagAlong() {
        return tagAlong;
    }

    public BigDecimal price() {
        return price;
    }

    public BigDecimal peRatio() {
        return peRatio;
    }

    public BigDecimal dividendYield() {
        return dividendYield;
    }

    public BigDecimal pbRatio() {
        return pbRatio;
    }

    public BigDecimal lastDividend() {
        return lastDividend;
    }

    public Boolean isBesst() {
        return isBesst;
    }

    public Boolean isNewMarket() {
        return isNewMarket;
    }
}
