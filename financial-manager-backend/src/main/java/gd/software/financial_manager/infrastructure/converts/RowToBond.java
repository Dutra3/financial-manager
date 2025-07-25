package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.infrastructure.persistence.relational.BondRow;

import java.util.List;

public class RowToBond {

    public static List<Bond> convert(List<BondRow> rows) {
        return rows.stream()
                .map(RowToBond::convert)
                .toList();
    }

    public static Bond convert(BondRow row) {
        return new Bond(row.getId(), row.getName(), row.getDescription(), row.getType(), row.getIndustrySegment(), row.getPrice());
    }
}
