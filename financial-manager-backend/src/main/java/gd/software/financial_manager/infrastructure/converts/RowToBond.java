package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.infrastructure.persistence.relational.BondRow;

public class RowToBond {

    public static Bond convert(BondRow row) {
        return new Bond(row.getId(), row.getName(), row.getTicker(), row.getDescription(), row.getQuantity(), row.getPrice(), null);
    }
}
