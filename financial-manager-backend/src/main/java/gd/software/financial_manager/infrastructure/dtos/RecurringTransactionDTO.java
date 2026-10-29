package gd.software.financial_manager.infrastructure.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record RecurringTransactionDTO(
        @NotBlank String name,
        String description,
        @NotNull BigDecimal amount,
        @NotNull LocalDate firstPaymentDate,
        @NotNull UUID categoryId,
        UUID accountId,
        @NotNull @Min(2) @Max(120) Integer occurrences,
        @NotBlank @Pattern(regexp = "WEEKLY|MONTHLY|YEARLY") String frequency
) {}
