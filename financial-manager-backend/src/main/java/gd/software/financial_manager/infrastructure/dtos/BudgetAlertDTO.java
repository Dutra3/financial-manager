package gd.software.financial_manager.infrastructure.dtos;

import gd.software.financial_manager.domain.model.BudgetAlert;

import java.math.BigDecimal;
import java.util.UUID;

public record BudgetAlertDTO(
        UUID categoryId,
        String categoryName,
        BigDecimal budgetAmount,
        BigDecimal spentAmount,
        BigDecimal percentage,
        String status
) {
    public static BudgetAlertDTO from(BudgetAlert alert) {
        return new BudgetAlertDTO(
                alert.categoryId(),
                alert.categoryName(),
                alert.budgetAmount(),
                alert.spentAmount(),
                alert.percentage(),
                alert.status().name()
        );
    }
}
