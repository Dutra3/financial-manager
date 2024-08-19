package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Wallet {

    private UUID id;
    private BigDecimal totalAmount;
    private List<Bond> bonds;
    private List<Stock> stocks;
    private List<Reit> reits;

    public Wallet(UUID id, BigDecimal totalAmount, List<Bond> bonds, List<Stock> stocks, List<Reit> reits) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.bonds = bonds;
        this.stocks = stocks;
        this.reits = reits;
    }

    public UUID idd() {
        return id;
    }

    public BigDecimal totalAmount() {
        return totalAmount;
    }

    public List<Bond> bonds() {
        return bonds;
    }

    public List<Stock> stocks() {
        return stocks;
    }

    public List<Reit> reits() {
        return reits;
    }
}
