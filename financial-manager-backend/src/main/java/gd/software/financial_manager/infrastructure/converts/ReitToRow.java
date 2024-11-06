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
                .type(reit.type())
                .industrySegment(reit.industrySegment())
                .price(reit.price())
                .build();
    }
}
