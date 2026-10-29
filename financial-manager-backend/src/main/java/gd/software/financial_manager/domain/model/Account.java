package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Account {

    private UUID id;
    private String name;
    private AccountType type;
    private BigDecimal initialBalance;
    private UUID userId;

    public Account(UUID id, String name, AccountType type, BigDecimal initialBalance, UUID userId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.initialBalance = initialBalance;
        this.userId = userId;
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    public AccountType type() {
        return type;
    }

    public BigDecimal initialBalance() {
        return initialBalance;
    }

    public UUID userId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
