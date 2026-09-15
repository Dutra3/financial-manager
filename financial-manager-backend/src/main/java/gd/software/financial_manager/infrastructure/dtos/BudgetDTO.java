package gd.software.financial_manager.infrastructure.dtos;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record BudgetDTO(
        UUID id,
        @NotNull UUID categoryId,
        @NotNull BigDecimal amount,
        @NotNull Integer month,
        @NotNull Integer year,
        String categoryName
) {}
