package gd.software.financial_manager.infrastructure.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record AssetResponse(
        UUID id,
        String ticker,
        String name,
        String assetType,
        String description,
        String type,
        String industrySegment,
        BigDecimal price,
        BigDecimal tagAlong,
        BigDecimal peRatio,
        BigDecimal dividendYield,
        BigDecimal pbRatio,
        BigDecimal lastDividend,
        Boolean isBesst,
        Boolean isNewMarket
) {}
