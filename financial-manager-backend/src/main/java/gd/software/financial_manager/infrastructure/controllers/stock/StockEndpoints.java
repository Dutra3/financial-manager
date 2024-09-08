package gd.software.financial_manager.infrastructure.controllers.stock;

import gd.software.financial_manager.domain.model.Stock;
import gd.software.financial_manager.domain.usecase.stock.CreateStock;
import gd.software.financial_manager.infrastructure.converts.DtoToStock;
import gd.software.financial_manager.infrastructure.converts.StockToDTO;
import gd.software.financial_manager.infrastructure.dtos.StockDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stocks")
public class StockEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(StockEndpoints.class);

    @Autowired
    private CreateStock createStock;

    @PostMapping
    public ResponseEntity<StockDTO> save(@RequestBody StockDTO stockDTO) {
        Stock stock = DtoToStock.convert(stockDTO);
        Stock savedStock = createStock.use(stock);

        logger.info("Saved stock {}.", savedStock.ticker());

        return ResponseEntity.ok(StockToDTO.convert(savedStock));
    }
}
