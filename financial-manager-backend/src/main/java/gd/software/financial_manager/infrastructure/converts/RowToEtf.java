package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Etf;
import gd.software.financial_manager.infrastructure.persistence.relational.EtfRow;

public class RowToEtf {

    public static Etf convert(EtfRow etfRow) {
        return new Etf(etfRow.getId(), etfRow.getName(), etfRow.getTicker(), etfRow.getDescription(), etfRow.getType(),
                etfRow.getIndustrySegment(), etfRow.getPrice());
    }
}
