package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Reit;
import gd.software.financial_manager.infrastructure.dtos.ReitDTO;

public class ReitToDTO {

    public static ReitDTO convert(Reit reit) {
        return new ReitDTO(reit.id(), reit.name(), reit.description(), reit.ticker(), reit.quantity(), reit.price(),
                reit.transactionDate());
    }
}
