package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class EtfTransaction {

    private UUID id;
    private Etf etf;
    private BigDecimal quantity;
    private BigDecimal price;
    private LocalDate transactionDate;

    public EtfTransaction(UUID id, Etf etf, BigDecimal quantity, BigDecimal price, LocalDate transactionDate) {
        this.id = id;
        this.etf = etf;
        this.quantity = quantity;
        this.price = price;
        this.transactionDate = transactionDate;
    }

    public UUID id() {
        return id;
    }

    public Etf etf() {
        return etf;
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
