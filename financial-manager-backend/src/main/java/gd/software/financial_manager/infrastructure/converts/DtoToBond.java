package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.infrastructure.dtos.BondDTO;

public class DtoToBond {

    public static Bond convert(BondDTO bondDTO) {
        return new Bond(bondDTO.id(), bondDTO.name(), bondDTO.ticker(), bondDTO.description(), bondDTO.quantity(),
                bondDTO.price(), bondDTO.transactionDate());
    }
}
