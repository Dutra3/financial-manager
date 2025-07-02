package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Reit;
import gd.software.financial_manager.infrastructure.persistence.relational.ReitRow;

import java.util.List;

public class RowToReit {

    public static List<Reit> convert(List<ReitRow> reits) {
        return reits.stream()
                .map(RowToReit::convert)
                .toList();
    }

    public static Reit convert(ReitRow reitRow) {
        return new Reit(reitRow.getId(), reitRow.getName(), reitRow.getTicker(), reitRow.getDescription(), reitRow.getType(),
                reitRow.getIndustrySegment(), reitRow.getPrice());
    }
}
