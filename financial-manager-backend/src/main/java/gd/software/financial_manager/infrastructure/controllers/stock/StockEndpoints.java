package gd.software.financial_manager.infrastructure.controllers.stock;

import gd.software.financial_manager.domain.model.Stock;
import gd.software.financial_manager.domain.model.StockTransaction;
import gd.software.financial_manager.domain.usecase.stock.CreateStockTransaction;
import gd.software.financial_manager.domain.usecase.stock.FetchStock;
import gd.software.financial_manager.infrastructure.converts.DtoToStockTransaction;
import gd.software.financial_manager.infrastructure.converts.StockToResponse;
import gd.software.financial_manager.infrastructure.converts.StockTransactionToDTO;
import gd.software.financial_manager.infrastructure.dtos.StockTransactionDTO;
import gd.software.financial_manager.infrastructure.dtos.StockResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/stocks")
public class StockEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(StockEndpoints.class);

    @Autowired
    private CreateStockTransaction createStockTransaction;

    @Autowired
    private FetchStock fetchStock;

    @PostMapping
    public ResponseEntity<StockTransactionDTO> save(@RequestBody StockTransactionDTO stockDTO) {
        StockTransaction stockTransaction = DtoToStockTransaction.convert(stockDTO);
        StockTransaction savedStockTransaction = createStockTransaction.use(stockTransaction);

        logger.info("Saved stock transaction {}.", savedStockTransaction.stock().ticker());

        return ResponseEntity.status(HttpStatus.CREATED).body(StockTransactionToDTO.convert(savedStockTransaction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockResponse> fetchStock(@PathVariable UUID id) throws Exception {
        Stock stock = fetchStock.use(id);
        logger.info("Get stock {}.", stock.ticker());

        return ResponseEntity.status(HttpStatus.OK).body((StockToResponse.convert(stock)));
    }

}
