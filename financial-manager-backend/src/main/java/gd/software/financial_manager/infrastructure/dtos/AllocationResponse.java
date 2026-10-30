package gd.software.financial_manager.infrastructure.dtos;

import java.math.BigDecimal;

public record AllocationResponse(
        String assetType,
        BigDecimal currentValue,
        BigDecimal percent
) {}
