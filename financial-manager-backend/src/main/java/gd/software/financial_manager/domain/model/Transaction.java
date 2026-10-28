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
    private Category category;
    private UUID userId;

    public Transaction(UUID id, String name, String description, BigDecimal amount, LocalDate paymentDate, Category category) {
        this(id, name, description, amount, paymentDate, category, null);
    }

    public Transaction(UUID id, String name, String description, BigDecimal amount, LocalDate paymentDate, Category category, UUID userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.category = category;
        this.userId = userId;
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

    public Category category() {
        return category;
    }

    public UUID userId() {
        return userId;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
