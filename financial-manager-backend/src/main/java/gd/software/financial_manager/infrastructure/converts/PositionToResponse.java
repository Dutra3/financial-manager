package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.PortfolioPosition;
import gd.software.financial_manager.infrastructure.dtos.PositionResponse;

public class PositionToResponse {

    public static PositionResponse convert(PortfolioPosition position) {
        return new PositionResponse(position.assetId(), position.asset().ticker(), position.asset().name(),
                position.asset().assetType().name(), position.quantity(), position.averagePrice(),
                position.totalInvested(), position.dividends(), position.currentValue(),
                position.profitLoss(), position.profitLossPercent());
    }
}
