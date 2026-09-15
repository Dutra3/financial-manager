package gd.software.financial_manager.infrastructure.dtos;

public record AuthResponse(
        String token,
        String userId,
        String email
) {}
