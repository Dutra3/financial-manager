package gd.software.financial_manager.infrastructure.dtos;

import jakarta.validation.constraints.NotBlank;

public record GoogleLoginRequest(
        @NotBlank String accessToken
) {}
