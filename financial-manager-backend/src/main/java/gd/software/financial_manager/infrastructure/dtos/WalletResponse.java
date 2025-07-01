package gd.software.financial_manager.infrastructure.dtos;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record WalletResponse(
        UUID id,
        BigDecimal totalAmount,
        List<BondResponse>bonds,
        List<StockResponse> stocks,
        List<ReitResponse> reits
) {}
