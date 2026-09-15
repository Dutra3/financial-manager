package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class BudgetAlert {

    private UUID categoryId;
    private String categoryName;
    private BigDecimal budgetAmount;
    private BigDecimal spentAmount;
    private BigDecimal percentage;
    private AlertStatus status;

    public BudgetAlert(UUID categoryId, String categoryName, BigDecimal budgetAmount,
                       BigDecimal spentAmount, BigDecimal percentage, AlertStatus status) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.budgetAmount = budgetAmount;
        this.spentAmount = spentAmount;
        this.percentage = percentage;
        this.status = status;
    }

    public UUID categoryId() { return categoryId; }
    public String categoryName() { return categoryName; }
    public BigDecimal budgetAmount() { return budgetAmount; }
    public BigDecimal spentAmount() { return spentAmount; }
    public BigDecimal percentage() { return percentage; }
    public AlertStatus status() { return status; }

    public enum AlertStatus {
        OK, WARNING, EXCEEDED
    }
}
