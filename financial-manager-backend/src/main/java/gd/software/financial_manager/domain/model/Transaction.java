package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Transaction {

    private UUID id;
    private String description;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private TransactionType type;

    public Transaction(UUID id, String description, BigDecimal amount, LocalDate paymentDate, TransactionType type) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.type = type;
    }

    public UUID id() {
        return id;
    }

    public String description() {
        return description;
    }

    public BigDecimal amount() {
        return amount;
    }

    public LocalDate paymentDate() {
        return paymentDate;
    }

    public TransactionType type() {
        return type;
    }
}
