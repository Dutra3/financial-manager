package gd.software.financial_manager.infrastructure.controllers.bond;

import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.domain.model.BondTransaction;
import gd.software.financial_manager.domain.usecase.bond.CreateBondTransaction;
import gd.software.financial_manager.domain.usecase.bond.FetchBond;
import gd.software.financial_manager.infrastructure.converts.BondToResponse;
import gd.software.financial_manager.infrastructure.converts.BondTransactionToDTO;
import gd.software.financial_manager.infrastructure.converts.DtoToBondTransaction;
import gd.software.financial_manager.infrastructure.dtos.BondResponse;
import gd.software.financial_manager.infrastructure.dtos.BondTransactionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bonds")
public class BondEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(BondEndpoints.class);

    @Autowired
    private CreateBondTransaction createBondTransaction;

    @Autowired
    private FetchBond fetchBond;

    @PostMapping
    public ResponseEntity<BondTransactionDTO> save(@RequestBody BondTransactionDTO bondTransactionDTO) {
        BondTransaction bondTransaction = DtoToBondTransaction.convert(bondTransactionDTO);
        BondTransaction savedBondTransaction = createBondTransaction.use(bondTransaction);

        logger.info("Saved bond transaction {},", savedBondTransaction.bond().name());

        return ResponseEntity.status(HttpStatus.OK).body(BondTransactionToDTO.convert(savedBondTransaction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BondResponse> fetchBond(@PathVariable UUID id) throws Exception {
        Bond bond = fetchBond.by(id);
        logger.info("Get bond {}.", bond.name());

        return ResponseEntity.status(HttpStatus.OK).body(BondToResponse.convert(bond));
    }

    @GetMapping
    public ResponseEntity<List<BondResponse>> fetchAllBonds() {
        List<Bond> bonds = fetchBond.all();
        logger.info("Get all bonds.");

        return ResponseEntity.status(HttpStatus.OK).body(BondToResponse.convert(bonds));
    }
}
