package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Reit;
import gd.software.financial_manager.infrastructure.persistence.relational.ReitRow;

public class RowToReit {

    public static Reit convert(ReitRow reitRow) {
        return new Reit(reitRow.getId(), reitRow.getName(), reitRow.getTicker(), reitRow.getDescription(),
                reitRow.getQuantity(), reitRow.getPrice(), reitRow.getTransactionDate(), reitRow.getAveragePrice()
        );
    }
}
