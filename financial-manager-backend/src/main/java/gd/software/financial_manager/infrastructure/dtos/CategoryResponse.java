package gd.software.financial_manager.infrastructure.dtos;

import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name,
        String type
) {}
