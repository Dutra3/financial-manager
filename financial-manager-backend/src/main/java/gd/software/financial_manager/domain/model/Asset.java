package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Asset {

    private UUID id;
    private String ticker;
    private String name;
    private AssetType assetType;
    private String description;
    private String type;
    private String industrySegment;
    private BigDecimal price;
    private BigDecimal tagAlong;
    private BigDecimal peRatio;
    private BigDecimal dividendYield;
    private BigDecimal pbRatio;
    private BigDecimal lastDividend;
    private Boolean isBesst;
    private Boolean isNewMarket;

    public Asset(UUID id) {
        this.id = id;
    }

    public Asset(UUID id, String ticker, String name, AssetType assetType, String description, String type,
                 String industrySegment, BigDecimal price, BigDecimal tagAlong, BigDecimal peRatio,
                 BigDecimal dividendYield, BigDecimal pbRatio, BigDecimal lastDividend,
                 Boolean isBesst, Boolean isNewMarket) {
        this.id = id;
        this.ticker = ticker;
        this.name = name;
        this.assetType = assetType;
        this.description = description;
        this.type = type;
        this.industrySegment = industrySegment;
        this.price = price;
        this.tagAlong = tagAlong;
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

    public String ticker() {
        return ticker;
    }

    public String name() {
        return name;
    }

    public AssetType assetType() {
        return assetType;
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

    public BigDecimal tagAlong() {
        return tagAlong;
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
