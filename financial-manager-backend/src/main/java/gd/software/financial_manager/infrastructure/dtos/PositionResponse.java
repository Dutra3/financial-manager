package gd.software.financial_manager.infrastructure.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record PositionResponse(
        UUID assetId,
        String ticker,
        String name,
        String assetType,
        BigDecimal quantity,
        BigDecimal averagePrice,
        BigDecimal totalInvested,
        BigDecimal dividends,
        BigDecimal currentValue,
        BigDecimal profitLoss,
        BigDecimal profitLossPercent
) {}
