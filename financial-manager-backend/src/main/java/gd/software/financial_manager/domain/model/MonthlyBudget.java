package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class MonthlyBudget {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal targetAmount;
    private Category category;

    public MonthlyBudget(UUID id, String name, String description, BigDecimal targetAmount, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.targetAmount = targetAmount;
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

    public BigDecimal targetAmount() {
        return targetAmount;
    }

    public Category category() {
        return category;
    }
}
