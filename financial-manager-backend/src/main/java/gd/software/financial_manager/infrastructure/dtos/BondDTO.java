package gd.software.financial_manager.infrastructure.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record BondDTO(
        UUID id,
        @NotBlank String name,
        String description,
        @NotNull BigDecimal quantity,
        @NotNull BigDecimal price
) {}
