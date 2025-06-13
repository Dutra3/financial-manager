package gd.software.financial_manager.infrastructure.dtos;

import java.math.BigDecimal;

public record DebitResponse(
        BigDecimal amount
) {}
