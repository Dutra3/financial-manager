package gd.software.financial_manager.infrastructure.dtos;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ReitTransactionDTO(
        UUID id,
        @NotNull UUID reitId,
        @NotNull BigDecimal quantity,
        @NotNull BigDecimal price,
        @NotNull LocalDate transactionDate
) {
    public ReitTransactionDTO {
        if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
    }
}
