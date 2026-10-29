package gd.software.financial_manager.infrastructure.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransferDTO(
        @NotNull UUID fromAccountId,
        @NotNull UUID toAccountId,
        @NotNull @Positive BigDecimal amount,
        @NotNull LocalDate date,
        String description
) {}
