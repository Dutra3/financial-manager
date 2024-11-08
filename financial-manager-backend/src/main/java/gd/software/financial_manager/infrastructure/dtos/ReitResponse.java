package gd.software.financial_manager.infrastructure.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record ReitResponse(
        UUID id,
        String name,
        String ticker,
        String description,
        String type,
        String industrySegment,
        BigDecimal price
) {}
