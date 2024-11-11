package gd.software.financial_manager.infrastructure.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record BondResponse(
        UUID id,
        String name,
        String description,
        String type,
        String industrySegment,
        BigDecimal price
) {}
