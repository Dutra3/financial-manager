package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Reit;
import gd.software.financial_manager.infrastructure.dtos.ReitDTO;

public class DtoToReit {

    public static Reit convert(ReitDTO reitDTO) {
        return new Reit(reitDTO.id(), reitDTO.name(), reitDTO.ticker(), reitDTO.description(), reitDTO.quantity(),
                reitDTO.price(), reitDTO.transactionDate(), reitDTO.price());
    }
}
