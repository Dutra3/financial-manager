package gd.software.financial_manager.infrastructure.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ProfileDTO(
        UUID id,
        @NotBlank String name,
        @NotBlank String profession,
        @NotNull BigDecimal salary,
        @NotNull Integer payDay,
        @NotNull BigDecimal initialBalance,
        @NotNull BigDecimal financialGoal
) {}
