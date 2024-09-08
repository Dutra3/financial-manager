package gd.software.financial_manager.infrastructure.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BondDTO(
        UUID id,
        @NotBlank String name,
        @NotBlank String ticker,
        String description,
        @NotNull BigDecimal quantity,
        @NotNull LocalDate transactionDate,
        @NotNull BigDecimal price
) {
    public BondDTO {
        if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
    }
}
