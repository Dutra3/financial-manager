package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.infrastructure.dtos.BondDTO;

public class BondToDTO {

    public static BondDTO convert(Bond bond) {
        return new BondDTO(bond.id(), bond.name(), bond.description(), bond.ticker(),
                bond.quantity(), bond.transactionDate(), bond.price());
    }
}
