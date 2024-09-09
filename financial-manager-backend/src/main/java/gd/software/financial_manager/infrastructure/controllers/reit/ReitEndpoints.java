package gd.software.financial_manager.infrastructure.controllers.reit;

import gd.software.financial_manager.domain.model.Reit;
import gd.software.financial_manager.domain.usecase.reit.CreateReit;
import gd.software.financial_manager.infrastructure.converts.DtoToReit;
import gd.software.financial_manager.infrastructure.converts.ReitToDTO;
import gd.software.financial_manager.infrastructure.dtos.ReitDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private CreateReit createReit;

    @PostMapping
    public ResponseEntity<ReitDTO> save(@RequestBody ReitDTO reitDTO) {
        Reit reit = DtoToReit.convert(reitDTO);
        Reit savedReit = createReit.use(reit);

        logger.info("Saved reit {}.", savedReit.ticker());

        return ResponseEntity.ok(ReitToDTO.convert(savedReit));
    }
}
