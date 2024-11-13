package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Etf;
import gd.software.financial_manager.infrastructure.persistence.relational.EtfRow;

public class EtfToRow {

    public static EtfRow convert(Etf etf) {
        return EtfRow.builder()
                .id(etf.id())
                .name(etf.name())
                .ticker(etf.ticker())
                .description(etf.description())
                .type(etf.type())
                .industrySegment(etf.industrySegment())
                .price(etf.price())
                .build();
    }
}
