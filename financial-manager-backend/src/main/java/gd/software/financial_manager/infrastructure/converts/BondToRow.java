package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.infrastructure.persistence.relational.BondRow;

public class BondToRow {

    public static BondRow convert(Bond bond) {
        return BondRow.builder()
                .id(bond.id())
                .name(bond.name())
                .description(bond.description())
                .quantity(bond.quantity())
                .price(bond.price())
                .build();
    }
}
