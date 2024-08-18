package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Transaction {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private TransactionType type;
    private Category category;

    public Transaction(UUID id, String name, String description, BigDecimal amount, LocalDate paymentDate,
                       TransactionType type, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.type = type;
        this.category = category;
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

    public BigDecimal amount() {
        return amount;
    }

    public LocalDate paymentDate() {
        return paymentDate;
    }

    public TransactionType type() {
        return type;
    }

    public Category category() {
        return category;
    }
}
