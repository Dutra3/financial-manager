package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Reit;
import gd.software.financial_manager.infrastructure.persistence.relational.ReitRow;

public class ReitToRow {

    public static ReitRow convert(Reit reit) {
        return ReitRow.builder()
                .id(reit.id())
                .name(reit.name())
                .ticker(reit.ticker())
                .description(reit.description())
                .quantity(reit.quantity())
                .price(reit.price())
                .transactionDate(reit.transactionDate())
                .averagePrice(reit.averagePrice())
                .build();
    }
}
