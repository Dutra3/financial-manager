package gd.software.financial_manager.infrastructure.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserData(
        @NotNull UUID id,
        @NotBlank String email,
        @NotBlank String password
) {}
