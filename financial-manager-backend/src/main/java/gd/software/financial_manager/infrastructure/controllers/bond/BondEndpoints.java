package gd.software.financial_manager.infrastructure.controllers.bond;

import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.domain.usecase.CreateBond;
import gd.software.financial_manager.infrastructure.converts.BondToDTO;
import gd.software.financial_manager.infrastructure.converts.DtoToBond;
import gd.software.financial_manager.infrastructure.dtos.BondDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bonds")
public class BondEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(BondEndpoints.class);

    @Autowired
    private CreateBond createBond;

    @PostMapping
    public ResponseEntity<BondDTO> save(@RequestBody BondDTO bondDTO) {
        Bond bond = DtoToBond.convert(bondDTO);
        Bond savedBond = createBond.use(bond);

        logger.info("Saved bond {}.", savedBond.ticker());

        return ResponseEntity.ok(BondToDTO.convert(savedBond));
    }
}
