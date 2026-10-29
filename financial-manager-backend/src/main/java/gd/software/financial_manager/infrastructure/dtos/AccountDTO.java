package gd.software.financial_manager.infrastructure.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountDTO(
        UUID id,
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "CHECKING|SAVINGS|CASH|INVESTMENT") String type,
        @NotNull BigDecimal initialBalance
) {}
