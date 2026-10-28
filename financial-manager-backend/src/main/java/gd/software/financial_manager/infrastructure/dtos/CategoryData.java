package gd.software.financial_manager.infrastructure.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record CategoryData(
        UUID id,
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "DEBIT|CREDIT") String type
) {}
