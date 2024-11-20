package gd.software.financial_manager.infrastructure.dtos;

import java.util.UUID;

public record CategoryData(
        UUID id,
        String name,
        String type
) {}
