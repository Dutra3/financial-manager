package gd.software.financial_manager.infrastructure.controllers.reit;

import gd.software.financial_manager.domain.model.Reit;
import gd.software.financial_manager.domain.model.ReitTransaction;
import gd.software.financial_manager.domain.usecase.reit.CreateReitTransaction;
import gd.software.financial_manager.domain.usecase.reit.FetchReit;
import gd.software.financial_manager.infrastructure.converts.DtoToReitTransaction;
import gd.software.financial_manager.infrastructure.converts.ReitToResponse;
import gd.software.financial_manager.infrastructure.converts.ReitTransactionToDTO;
import gd.software.financial_manager.infrastructure.dtos.ReitResponse;
import gd.software.financial_manager.infrastructure.dtos.ReitTransactionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reits")
public class ReitEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(ReitEndpoints.class);

    @Autowired
    private CreateReitTransaction createReitTransaction;

    @Autowired
    private FetchReit fetchReit;

    @PostMapping
    public ResponseEntity<ReitTransactionDTO> save(@RequestBody ReitTransactionDTO reitTransactionDTO) {
        ReitTransaction reitTransaction = DtoToReitTransaction.convert(reitTransactionDTO);
        ReitTransaction savedReitTransaction = createReitTransaction.use(reitTransaction);

        logger.info("Saved reit transaction {}.", savedReitTransaction.reit().ticker());

        return ResponseEntity.status(HttpStatus.CREATED).body(ReitTransactionToDTO.convert(savedReitTransaction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReitResponse> fetchReit(@PathVariable UUID id) throws Exception {
        Reit reit = fetchReit.by(id);
        logger.info("Get reit {}.", reit.ticker());
        return ResponseEntity.status(HttpStatus.OK).body(ReitToResponse.convert(reit));
    }

    @GetMapping
    public ResponseEntity<List<ReitResponse>> fetchAllReits() {
        List<Reit> reits = fetchReit.all();
        logger.info("Get all reits.");

        return ResponseEntity.status(HttpStatus.OK).body(ReitToResponse.convert(reits));
    }
}
