package gd.software.financial_manager.infrastructure.controllers.reit;

import gd.software.financial_manager.domain.model.ReitTransaction;
import gd.software.financial_manager.domain.usecase.reit.CreateReitTransaction;
import gd.software.financial_manager.infrastructure.converts.DtoToReitTransaction;
import gd.software.financial_manager.infrastructure.converts.ReitTransactionToDTO;
import gd.software.financial_manager.infrastructure.dtos.ReitTransactionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reits")
public class ReitEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(ReitEndpoints.class);

    @Autowired
    private CreateReitTransaction createReitTransaction;

    @PostMapping
    public ResponseEntity<ReitTransactionDTO> save(@RequestBody ReitTransactionDTO reitTransactionDTO) {
        ReitTransaction reitTransaction = DtoToReitTransaction.convert(reitTransactionDTO);
        ReitTransaction savedReitTransaction = createReitTransaction.use(reitTransaction);

        logger.info("Saved reit transaction {}.", savedReitTransaction.reit().ticker());

        return ResponseEntity.status(HttpStatus.CREATED).body(ReitTransactionToDTO.convert(savedReitTransaction));
    }
}
