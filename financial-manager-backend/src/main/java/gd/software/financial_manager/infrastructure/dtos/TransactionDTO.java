package gd.software.financial_manager.infrastructure.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionDTO(
        UUID id,
        @NotBlank String description,
        @NotNull BigDecimal amount,
        @NotNull LocalDate paymentDate
) {}
