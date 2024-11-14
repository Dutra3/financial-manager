package gd.software.financial_manager.infrastructure.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        String name,
        String description,
        BigDecimal amount,
        LocalDate paymentDate,
        String type,
        String category
) {}
