package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Stock;
import gd.software.financial_manager.infrastructure.dtos.StockDTO;

public class DtoToStock {

    public static Stock convert(StockDTO stockDTO) {
        return new Stock(stockDTO.id(), stockDTO.name(), stockDTO.ticker(), stockDTO.description(), stockDTO.quantity(),
                stockDTO.price(), stockDTO.transactionDate());
    }
}
